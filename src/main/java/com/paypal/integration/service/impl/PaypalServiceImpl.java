package com.paypal.integration.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.integration.config.AppProperties;
import com.paypal.integration.config.FEParamsEnum;
import com.paypal.integration.model.FEParams;
import com.paypal.integration.model.UserTransactions;
import com.paypal.integration.model.Users;
import com.paypal.integration.service.dao.PaypalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaypalServiceImpl implements PaypalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaypalServiceImpl.class);

    private final TransactionServiceImpl transactionService;
    private final FEParamsServiceImpl feParamsService;
    private final AppProperties appProperties;
    private final UsersServiceImpl usersService;

    @Autowired
    public PaypalServiceImpl(final TransactionServiceImpl transactionService,
                             final FEParamsServiceImpl feParamsService,
                             final AppProperties appProperties,
                             final UsersServiceImpl usersService) {
        this.transactionService = transactionService;
        this.feParamsService = feParamsService;
        this.appProperties = appProperties;
        this.usersService = usersService;
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(
                appProperties.getPaypalConfig().getClientId(),
                appProperties.getPaypalConfig().getClientSecret(),
                appProperties.getPaypalConfig().getMode()
        );

        return payment.execute(apiContext, paymentExecution);
    }


    @Override
    public String authorizeUserPayment(UserTransactions userTransaction) throws PayPalRESTException {
        Payer payer = getPayer(userTransaction.getUserId());
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getPaypalUserTransInfor(userTransaction);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent(appProperties.getPaypalConfig().getIntent());

        APIContext apiContext = new APIContext(
                appProperties.getPaypalConfig().getClientId(),
                appProperties.getPaypalConfig().getClientSecret(),
                appProperties.getPaypalConfig().getMode()
        );

        Payment approvedPayment = requestPayment.create(apiContext);

        // save to database
        this.transactionService.saveNewTransaction(userTransaction);

        return extractApprovalLink(approvedPayment);
    }

    private List<Transaction> getPaypalUserTransInfor(UserTransactions userTransaction) {
        Details details = new Details();
        details.setShipping(String.valueOf(userTransaction.getShippingCost()));
        details.setSubtotal(String.valueOf(userTransaction.getProductCost()));
        details.setTax(String.valueOf(userTransaction.getTax()));

        Amount amount = new Amount();
        amount.setCurrency(userTransaction.getCurrency());
        amount.setTotal(String.valueOf(userTransaction.getTotalCost()));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(userTransaction.getProductName());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency(userTransaction.getCurrency());
        item.setName(userTransaction.getProductName());
        item.setPrice(String.valueOf(userTransaction.getProductCost()));
        item.setTax(String.valueOf(userTransaction.getTax()));
        // change if you want to
        item.setQuantity(appProperties.getPaypalConfig().getQuantity());

        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private Payer getPayer(Long id) {
        Payer payer = new Payer();
        payer.setPaymentMethod(appProperties.getPaypalConfig().getMethod());

        Optional<Users> user = this.usersService.getUserById(id);
        if (!user.isPresent()) {
            LOGGER.error("User with {} id not present", id);
            return null;
        }

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(user.get().getName())
                .setLastName(user.get().getLastName())
                .setEmail(user.get().getEmail());

        payer.setPayerInfo(payerInfo);
        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();

        Optional<FEParams> successUrl = this.feParamsService.getFEParamById(FEParamsEnum.CALLBACK_SUCCESS_LINK.getId());
        Optional<FEParams> failureUrl = this.feParamsService.getFEParamById(FEParamsEnum.CALLBACK_FAILURE_LINK.getId());

        if (!successUrl.isPresent() || !failureUrl.isPresent()) {
            LOGGER.error("Success and failure url not found");
            return null;
        }

        redirectUrls.setCancelUrl(failureUrl.get().getValue());
        redirectUrls.setReturnUrl(successUrl.get().getValue());

        return redirectUrls;
    }

    private String extractApprovalLink(Payment payment) {
        List<Links> links = payment.getLinks();
        String approvedLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvedLink = link.getHref();
                break;
            }
        }

        return approvedLink;
    }

}
