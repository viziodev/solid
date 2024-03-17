package com.solid;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employe {
    private int id ;
    private String nom;
    private int age;
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * InnerEmploye
     */
    @Data
    @AllArgsConstructor
    public class EmployeDto {
        private long id ;
        private String nom;
        public EmployeDto(JSONObject employee ) {
            //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("employee");
         
        id = (long)employeeObject.get("id");    
        nom = (String) employeeObject.get("nom");  
 
        }
        
    }

     public Employe(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    @SuppressWarnings("unchecked")
    public void saveEmploye(){
        JSONObject employeeDetails = new JSONObject();
         employeeDetails.put("id", id);
         employeeDetails.put("nom", nom);
         employeeDetails.put("age", age);
         JSONObject employeeObject = new JSONObject(); 
         employeeObject.put("employee", employeeDetails);
         JSONArray employeeList = getListEmp();
         employeeList.add(employeeObject);
         try (FileWriter file = new FileWriter("data/employees.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(employeeList.toJSONString()); 
            file.flush();

          } catch (IOException e) {
            e.printStackTrace();
          }

    }
    public Employe load(String json) throws IOException {
      return objectMapper.readValue(
        json, Employe.class);
    }
    @SuppressWarnings("unchecked")
    public String toJson(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json=null;
       
        try {   
            JSONArray employeeList= getListEmp(); 
            List<EmployeDto> employeDtos=new ArrayList<>();
            for (Object emp : employeeList) {
                JSONObject obj= (JSONObject)emp;
                employeDtos.add(new EmployeDto(obj)); 
            }
             json = ow.writeValueAsString(employeDtos);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
    public JSONArray getListEmp(){
          JSONParser jsonParser = new JSONParser();
         JSONArray employeeList=null;
        try (FileReader reader = new FileReader("data/employees.json"))
        {
            //Read JSON file
             Object obj;
            try {
                obj = jsonParser.parse(reader);
                employeeList = (JSONArray) obj;
               
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return employeeList;
    }
}
