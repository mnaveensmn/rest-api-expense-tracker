package com.naveen.explorations.customannotations;

import com.naveen.explorations.customannotations.beans.Person;
import com.naveen.explorations.customannotations.jsonconverter.ObjectToJsonConverter;

public class JsonConversion {

    public static void main(String arg[]) {
        Person person = new Person();
        person.setFirstName("Naveen");
        person.setLastName("Kumar");
        person.setAge("27");
        person.setAddress("Tirupur");

        ObjectToJsonConverter objectToJsonConverter = new ObjectToJsonConverter();
        try {
            String jsonString = objectToJsonConverter.convertToJson(person);
            System.out.println(jsonString);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
