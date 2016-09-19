package ru.dostavkamix.denis.dostavkamix.model.account;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Random;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dostavkamix.denis.dostavkamix.model.account.api.AccountAPIService;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.*;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Login;
import rx.Subscriber;

/**
 * Created by Денис on 04.09.2016.
 *
 * @author Denis Tkachenko
 */

public class AccountManagerTest {

    @Test
    public void createUser() throws Exception {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cabinet.chaihanamix.ru/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountAPIService service = retrofit.create(AccountAPIService.class);

        service.createUser(new User("test", getRandomEmail(), "12345", "23.09.1996", "TermometR123123", null))
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Created completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        System.out.println("status: " + userResponse.isStatus());
                        if(userResponse.isStatus()) {
                            System.out.println("response: name: " + userResponse.getUser().getName());
                            System.out.println("response: phone: " + userResponse.getUser().getPhone());
                            System.out.println("response: birthday: " + userResponse.getUser().getBirthday());
                            System.out.println("response: email: " + userResponse.getUser().getEmail());
                            System.out.println("response: updated_at: " + userResponse.getUser().getUpdatedAt());
                            System.out.println("response: created_at: " + userResponse.getUser().getCreatedAt());
                            System.out.println("response: id: " + userResponse.getUser().getId());
                        } else {
                            System.out.println("msg: " + userResponse.getMsg());
                            System.out.println("error: name: " + userResponse.getErrors().getName());
                            System.out.println("error: email: " + userResponse.getErrors().getEmail());
                            System.out.println("error: birthday: " + userResponse.getErrors().getBirthday());
                            System.out.println("error: phone: " + userResponse.getErrors().getPhone());
                            System.out.println("error: password: " + userResponse.getErrors().getPassword());
                        }
                    }
                });
    }

    @Test
    public void getToken() throws Exception {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cabinet.chaihanamix.ru/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountAPIService service = retrofit.create(AccountAPIService.class);

        service.getToken(new Login("test1@test.com", "TermometR123123", "123456"))
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Gated token completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Token token) {
                        Assert.assertTrue(token.isStatus());
                        System.out.println("status: " + token.isStatus());
                        if(token.isStatus()) {
                            System.out.println("token: user_id: " + token.getUser_id());
                            System.out.println("token: access_token: " + token.getAccess_token());
                        } else {
                            System.out.println("msg: " + token.getMsg());
                        }

                    }
                });
    }

    @Test
    public void getUser() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cabinet.chaihanamix.ru/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountAPIService service = retrofit.create(AccountAPIService.class);

        service.getToken(new Login("test1@test.com", "TermometR123123", "123456"))
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Receipt token completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Token token) {
                        Assert.assertTrue(token.isStatus());
                        System.out.println("status: " + token.isStatus());
                        if(token.isStatus()) {
                            System.out.println("token: user_id: " + token.getUser_id());
                            System.out.println("token: access_token: " + token.getAccess_token());

                            service.getUser(token.getAccess_token())
                                    .subscribe(new Subscriber<User>() {
                                        @Override
                                        public void onCompleted() {
                                            System.out.println("Receipt user completed");
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                        }

                                        @Override
                                        public void onNext(User user) {
                                            System.out.println("user: name: " + user.getUser().getName());
                                        }
                                    });
                        } else {
                            System.out.println("msg: " + token.getMsg());
                        }

                    }
                });
    }

    @Test
    public void updateUser() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cabinet.chaihanamix.ru/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountAPIService service = retrofit.create(AccountAPIService.class);

        service.getToken(new Login("test1@test.com", "TermometR123123", "123456"))
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Receipt token completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Token token) {
                        Assert.assertTrue(token.isStatus());
                        System.out.println("status: " + token.isStatus());
                        if(token.isStatus()) {
                            System.out.println("token: user_id: " + token.getUser_id());
                            System.out.println("token: access_token: " + token.getAccess_token());

                            service.getUser(token.getAccess_token())
                                    .subscribe(new Subscriber<User>() {
                                        @Override
                                        public void onCompleted() {
                                            System.out.println("Receipt user completed");
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                        }

                                        @Override
                                        public void onNext(User user) {
                                            System.out.println("user: name: " + user.getUser().getName());
                                            service.updateUser(token.getAccess_token(), user.getUser())
                                                    .subscribe(new Subscriber<UserResponse>() {
                                                        @Override
                                                        public void onCompleted() {
                                                            System.out.println("Update user completed");
                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                            e.printStackTrace();
                                                        }

                                                        @Override
                                                        public void onNext(UserResponse userResponse) {
                                                            Assert.assertTrue(userResponse.isStatus());
                                                            if (userResponse.isStatus()) System.out.println("successes updating");
                                                        }
                                                    });
                                        }
                                    });
                        } else {
                            System.out.println("msg: " + token.getMsg());
                        }

                    }
                });
    }

    private String getRandomEmail() {
        Random r = new Random(System.currentTimeMillis());
        return r.nextInt(1000) + "@mail.com";
    }
}
