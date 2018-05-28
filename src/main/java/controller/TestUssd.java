package controller;

import dao.HibernateServerDAOImpl;
import dao.RecordSetDAO;
import model.SessionTrack;
import model.User;
import org.apache.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/test")
public class TestUssd {

    private RecordSetDAO<User> userDAO = new HibernateServerDAOImpl<User>(User.class);
    private RecordSetDAO<SessionTrack> sessionTrackDAO = new HibernateServerDAOImpl<SessionTrack>(SessionTrack.class);

    Helper helper = new Helper();

    @Path("/basic")
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.TEXT_PLAIN)
    public Response basicRegistration(MultivaluedMap<String, String> formParams) throws Exception {

        String sessionId = "";
        String phoneNumber = "";
        String serviceCode = "";
        String text = "";
        String prevText = "";
        String response = "";

        List<User> users = null;

        try {
            sessionId = formParams.getFirst("sessionId");
            System.out.println("Session Id Received:::::::::: " + sessionId);

            phoneNumber = formParams.getFirst("phoneNumber");
            System.out.println("phoneNumber Received:::::::::: " + phoneNumber);

            serviceCode = formParams.getFirst("serviceCode");
            System.out.println("serviceCode Received:::::::::: " + serviceCode);

            text = formParams.getFirst("text");
            System.out.println("text Received:::::::::: " + text);

            prevText = lastSessionText(sessionId);
            System.out.println("Previous text is :::::: " + prevText);

            helper.saveSession(sessionId, phoneNumber, serviceCode, text);


        } catch (Exception ex) {
            Logger.getLogger(TestUssd.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        if (text.isEmpty()) {

            response = "CON Welcome to NHIF Self Service \n";
            response += "1. Register \n";
            response += "2. Login \n";

            return Response.status(HttpStatus.SC_OK)
                    .entity(response).build();

        } else if (text.equalsIgnoreCase("1")) {

            response = "CON Enter your Name & ID Number separated by a comma: \n e.g John Doe,3002001 ";

            return Response.status(HttpStatus.SC_OK)
                    .entity(response).build();

        } else if (text.equalsIgnoreCase("2")) {

            response = "CON Enter your ID Number to Login: \n";

            return Response.status(HttpStatus.SC_OK)
                    .entity(response).build();

        }  else if (prevText.equalsIgnoreCase("1")) {
            System.out.println("Text is :::::  " + text);
            String nameAndidNumber = text.replaceAll("1\\*", "");
            System.out.println("Name & Id number is :::::::: " + nameAndidNumber);

            //String[] nameAndIdSplit = nameAndidNumber.split(",");
            String[] parts = nameAndidNumber.split(",");
            String name = parts[0];
            System.out.println("Name after slit ::::: " + name);
            String idNumber = parts[1];
            System.out.println("ID after slit ::::: " + idNumber);

            boolean registered = registerUser(idNumber.trim(), phoneNumber, name);

            if (registered) {
                response = "END Successfully Registered";
            } else {
                response = "END A problem occurred, try again later";
            }

            return Response.status(HttpStatus.SC_OK)
                    .entity(response).build();
        }  else if (prevText.equalsIgnoreCase("2")) {
            System.out.println("Text is :::::  " + text);

            String idNumber = text.replaceAll("2\\*", "");

            users = userById(idNumber);
            if (!users.isEmpty()) {

                response = "CON What would you like to do? \n";
                response += "1. View Member details \n";
                response += "2. Check my status \n";
                response += "3. Pay via MPESA \n";
                response += "3. View My Dependants \n";

                return Response.status(HttpStatus.SC_OK)
                        .entity(response).build();

            } else {

                response = "END You are not a registered member \n";

                return Response.status(HttpStatus.SC_OK)
                        .entity(response).build();
            }

        } else if (text.equalsIgnoreCase(prevText+"1")) {
            System.out.println("Text is ::: " + text);

            List<User> users1 = userByPhone(phoneNumber);
            if (!users1.isEmpty()) {
                response = "END Name: " + users1.get(0).getNames() + "\n";
                response += "Phone Number: " + users1.get(0).getPhoneNumber() + "\n";
                response += "ID Number: " + users1.get(0).getNationalId() + "\n";
            } else {
                response  = "END You are not registered";
            }

            return Response.status(HttpStatus.SC_OK)
                    .entity(response).build();
        }

        return null;
    }

    private List<User> userById(String idNumber) {

        List<User> users = new ArrayList<>();

        try {
            List<String> _fkFieldName = new ArrayList<String>();
            List<Object> _fkFieldVal = new ArrayList<Object>();
            _fkFieldName.add(User.NATIONAL_ID);
            _fkFieldVal.add(idNumber);
            users = userDAO
                    .readByFields(_fkFieldName, _fkFieldVal, User.CREATED_AT, 1, 0, 1);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    private List<User> userByPhone(String phoneNumber) {

        List<User> users = new ArrayList<>();

        try {
            List<String> _fkFieldName = new ArrayList<String>();
            List<Object> _fkFieldVal = new ArrayList<Object>();
            _fkFieldName.add(User.PHONE_NUMBER);
            _fkFieldVal.add(phoneNumber);
            users = userDAO
                    .readByFields(_fkFieldName, _fkFieldVal, User.CREATED_AT, 1, 0, 1);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    private String lastSessionText(String sessId) {

        List<SessionTrack> sessionTracks = new ArrayList<>();

        String sessionText = "";

        try {
            List<String> _fkFieldName = new ArrayList<String>();
            List<Object> _fkFieldVal = new ArrayList<Object>();
            _fkFieldName.add(SessionTrack.SESS_ID);
            _fkFieldVal.add(sessId);
            sessionTracks = sessionTrackDAO
                    .readByFields(_fkFieldName, _fkFieldVal, SessionTrack.CREATED_AT, 1, 0, 1);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!sessionTracks.isEmpty()) {
            sessionText = sessionTracks.get(0).getTextReceived();
        }

        System.out.println("Session text found ::::::: " + sessionText);

        return sessionText;
    }

    public boolean registerUser(String nationalId, String phoneNumber, String names) {

        boolean response = false;
        try {

            User user = new User();
            user.setNames(names);
            user.setPhoneNumber(phoneNumber);
            user.setNationalId(nationalId);

            userDAO.create(user);

            response= true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  response;
    }

}