package thanhlam.com;

import Model.BaseModel;
import Model.Company;
import Model.Customer;
import Model.Product;
import Validation.CommonValidator;
import Validation.IValidator;

import java.lang.reflect.*;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String VALIDATOR_PACKAGE = "Validation.%sValidator";

    public static void main(String[] args) {
        BaseModel customer = new Customer();
        BaseModel company = new Company();
        BaseModel product = new Product();

        binding(customer, initCustomerData());
        binding(company, initCompanyData());
        binding(product, initProductData());

        System.out.println("Customer " + customer);
        System.out.println("Company " + company);
        System.out.println("Product " + product);

        validate(customer);
        validate(company);
        validate(product);
    }

    /***
     * general binding data to model object.
     * @param model
     * @param data
     */

    private static void binding(BaseModel model, Map<String, Object> data){
        Field field =  null;
        Class<?> objClass = model.getClass();
        if(!data.isEmpty()){
            String fieldName ="";
            Object value = null;

            // loop and get data field
            for(Map.Entry<String, Object> entry : data.entrySet()) {
                fieldName = entry.getKey();
                value = entry.getValue();
                try{
                    //get field access
                    field = objClass.getDeclaredField(fieldName);
                    setFieldValue(model, field, value);
                } catch (Exception e){
                    System.out.println(String.format("Can not get field %s, model %s", fieldName, objClass.getSimpleName()));
                }

            }
        }
    }

    /***
     * Set value for field use reflection
     * @param model
     * @param fieldName
     * @param value
     */

    private static void setFieldValue(BaseModel model, Field field, Object value){
        try {
            field.setAccessible(true);
            field.set(model,value);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    /***
     * Model validation uses the suitable validator in validation package.
     * @param model
     */

    private static void validate(BaseModel model) {
        Class<?> objClass = model.getClass();
        //get class name of model
        String className = objClass.getSimpleName();
        String validatorClassName = String.format(VALIDATOR_PACKAGE, className);


        //init default validator
        IValidator validator = new CommonValidator();

        try {
            //get validator class from name
            Class validatorClass = Class.forName(validatorClassName);
            //check class has implement IValidator
            if(IValidator.class.isAssignableFrom(validatorClass)){
                //init validator class
                validator = (IValidator) validatorClass.newInstance();
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Can not find valdator class " + validatorClassName);
        } catch (IllegalAccessException e){
            System.out.println("IllegalAccessException " + e.getMessage());
        } catch (InstantiationException e){
            System.out.println("InstantiationException " + e.getMessage());
        }
        validator.Validate();
    }

    private static Map initCustomerData(){
        Map<String, Object> data = new HashMap<String, Object>(4);
        data.put("firstName", "Join");
        data.put("lastName", "Smith");
        data.put("age", 26);
        return data;
    }
    private static Map initCompanyData(){
        Map<String, Object> data = new HashMap<String, Object>(2);
        data.put("name", "ARG");
        data.put("adress", "39 No Trang Long, Tp.HCM");
        return data;
    }
    private static Map initProductData(){
        Map<String, Object> data = new HashMap<String, Object>(2);
        data.put("name", "Chocolate");
        data.put("price", 5);
        return data;
    }
}
