package com.weblieu.findtrue.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cashfree.pg.CFPaymentService;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.model.Res;
import com.weblieu.findtrue.repositry.Api;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitApiClient;
import com.weblieu.findtrue.service.AppConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_BANK_CODE;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_CVV;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_HOLDER;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_MM;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_NUMBER;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_YYYY;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static com.cashfree.pg.CFPaymentService.PARAM_PAYMENT_OPTION;
import static com.cashfree.pg.CFPaymentService.PARAM_UPI_VPA;
import static com.cashfree.pg.CFPaymentService.PARAM_WALLET_CODE;

public class PaymentActivity extends AppCompatActivity {

    private Button payment;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    Api api;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        payment = (Button)findViewById(R.id.payment);
        api = RetrofitApiClient.getInstance().create(Api.class);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatepayment();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
//        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        System.out.println("TAG--------------------------------------API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        System.out.println("hderffff-------------------"+key + " : " + bundle.getString(key));
                    }
                }
        }
    }

    private void initiatepayment(){
        Random r = new Random();
        int randomNumber = r.nextInt(999999);
        Map<String,String> dataSend = new HashMap<>();
        dataSend.put("appId", "54709fd88ae1ff17a3811256f90745");
        dataSend.put("orderId", String.valueOf(randomNumber));
        dataSend.put("orderAmount", String.valueOf(100));
        dataSend.put("customerPhone", "7065263139");
        dataSend.put("customerEmail", "test@gmail.com");

       // String orderid = "1234565567";
        String amount = "100";
        compositeDisposable.add(api.getToken(String.valueOf(randomNumber), amount, "INR").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Res>() {
            @Override
            public void accept(Res res) throws Exception {
                if (res.getStatus().equals("OK")){
                    System.out.println("-----------------------------"+res.getMessage());
                    CFPaymentService.getCFPaymentServiceInstance().doPayment(PaymentActivity.this, dataSend, res.getCftoken(), "TEST", "#F8A31A", "#FFFFFF", false);
                }else {
                    Toast.makeText(PaymentActivity.this, res.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(PaymentActivity.this, ""+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }
        }));

    }
}

