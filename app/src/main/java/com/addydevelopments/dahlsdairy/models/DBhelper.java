package com.addydevelopments.dahlsdairy.models;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DBhelper {

    //Constructor
    public DBhelper() {
    }

    //Method to retrieve all routes
    public List<Route> getRoutes() throws ExecutionException, InterruptedException {

        //Initialize variables
        List<Route> routes = new ArrayList<>();

        //Start backgroung thread and retrieve data
        ExecutorService executor = Executors.newSingleThreadExecutor();
        RoutesWorkerClass routeWorker = new RoutesWorkerClass();
        Future<List<Route>> futures = executor.submit(routeWorker);
        executor.shutdown();

        if (futures.get() != null) {
            routes = futures.get();
        }

        return routes;

    }

    //Helper class to run on the background thread
    private class RoutesWorkerClass implements Callable<List<Route>> {

        List<Route> routesTemp = new ArrayList<>();

        @Override
        public List<Route> call() throws Exception {
            String str = "http://10.0.2.2:8082/getRoutes";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            List<Route> routeList = new ArrayList<>();


            try {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    JSONObject jsonObject = new JSONObject(line);
                    Route route = new Route();
                    route.setRouteName(jsonObject.getString("routeName"));
                    route.setCustomerIDs(jsonObject.getString("routeStops"));
                    routeList.add(route);

                }

                System.out.println(stringBuffer);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return routeList;

        }
    }



    public List<Customer> getCustomerJSONS() throws ExecutionException, InterruptedException {

        List<Customer> customers = new ArrayList<>();
        ;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<Customer>> futures = executorService.submit(new getCustomerJSON());
        executorService.shutdown();

        if (futures.get() != null) {
            customers = futures.get();
        }


        return customers;
    }

    //Helper class to run on the background thread
    class getCustomerJSON implements Callable<List<Customer>> {
        @Override
        public List<Customer> call() throws Exception {
            String str = "http://10.0.2.2:8082/getCustomers";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            List<Customer> customerList = new ArrayList<>();


            try {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    JSONObject jsonObject = new JSONObject(line);
                    Customer customer = new Customer();
                    customer.setCustomerID(jsonObject.getString("_id"));
                    customer.setLastName(jsonObject.getString("lastName"));
                    customer.setFirstName(jsonObject.getString("firstName"));
                    customer.setPhoneNumber(jsonObject.getString("phoneNumber"));
                    customer.setAddress(jsonObject.getString("address"));
                    customer.setBalance(jsonObject.getString("balance"));
                    customerList.add(customer);

                }

                System.out.println(stringBuffer);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return customerList;
        }
    }

    public List<Customer> getCustomerJSONSByName(String customerIDS) throws ExecutionException, InterruptedException {

        List<Customer> customerList = new ArrayList<>();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<Customer>> futures = executorService.submit(new getCustomerJSONByName(customerIDS));
        executorService.shutdown();

        if (futures.get() != null) {
            customerList = futures.get();
        }


        return customerList;
    }

    //Helper class to run on the background thread
    class getCustomerJSONByName implements Callable<List<Customer>> {
        //Establish variable
        String string;

        //Constructor
        public getCustomerJSONByName(String string){
            this.string = string;
        }

        //Takes a string list of mongoDB Id's and formats it to send via http to webserver
        @Override
        public List<Customer> call() throws Exception {
            string = string.replace("[", "");
            string = string.replace("]","");
            string = string.replace(" ", "");
            String str = "http://10.0.2.2:8082/getCustomersByID?customerIDs=" + string.replace("\"", "'");
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            List<Customer> customerList = new ArrayList<>();


            try {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    JSONObject jsonObject = new JSONObject(line);
                    Customer customer = new Customer();
                    customer.setCustomerID(jsonObject.getString("_id"));
                    customer.setLastName(jsonObject.getString("lastName"));
                    customer.setFirstName(jsonObject.getString("firstName"));
                    customer.setPhoneNumber(jsonObject.getString("phoneNumber"));
                    customer.setAddress(jsonObject.getString("address"));
                    customer.setBalance(jsonObject.getString("balance"));
                    customerList.add(customer);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return customerList;
        }
    }


    public List<Product> getProductJSONS() throws ExecutionException, InterruptedException {

        List<Product> products = new ArrayList<>();
        ;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<Product>> futures = executorService.submit(new getProductJSON());
        executorService.shutdown();

        if (futures.get() != null) {
            products = futures.get();
        }


        return products;
    }

    //Helper class to run on the background thread
    class getProductJSON implements Callable<List<Product>> {
        @Override
        public List<Product> call() throws Exception {
            String str = "http://10.0.2.2:8082/getProducts";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            List<Product> productList = new ArrayList<>();


            try {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    JSONObject jsonObject = new JSONObject(line);
                    Product product = new Product();
                    product.setProductID(jsonObject.getString("_id"));
                    product.setProductName(jsonObject.getString("productName"));
                    product.setProductPrice(Double.parseDouble(jsonObject.getString("productPrice")));
                    product.setProductType(jsonObject.getString("productType"));
                    productList.add(product);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return productList;
        }
    }


    public void createInvoice(final Order order) throws ExecutionException, InterruptedException {


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {
            public void run() {

                String orderMongo = "http://10.0.2.2:8082/createInvoice?lastName=" + order.getLastName() + "&firstName="
                        + order.getFirstName() + "&products=";
                for (Product product: order.getProducts()){
                    orderMongo+= removeSpaces(product.getProductName())
                    + "," + product.getQuantity() + "," +
                    product.getProductPrice() + ",";
                }
                System.out.print(orderMongo);

                URL url = null;
                try {
                    url = new URL(orderMongo);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int responseCode = 0;
                try {
                    responseCode = urlConnection.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream inputStream = null;
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    inputStream = urlConnection.getErrorStream();
                } else {
                    try {
                        inputStream = urlConnection.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                urlConnection.disconnect();


            }
        });

        executorService.shutdown();
    }

    //Method to remove spaces from string
    public String removeSpaces(String str){
        str = str.replace(" ", "-");
        return str;
    }




}


