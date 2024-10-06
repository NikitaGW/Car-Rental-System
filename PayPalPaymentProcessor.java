package model.Payment;

public class PayPalPaymentProcessor implements PaymentProcessor{
    public boolean processPayment(double amount){
        System.out.println("Amount: "+amount+" process successfully through Paypal");
        return true;
    }
}
