package controller;

import dao.HibernateServerDAOImpl;
import dao.RecordSetDAO;
import model.SessionTrack;
import model.User;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Helper {

    private RecordSetDAO<User> userDAO = new HibernateServerDAOImpl<User>(User.class);

    private RecordSetDAO<SessionTrack> sessionTrackDAO = new HibernateServerDAOImpl<SessionTrack>(SessionTrack.class);

    public static JSONObject getObjectFromInputStream(InputStream incomingData) throws Exception {
        StringBuilder sb = new StringBuilder();
        String data = "";
        InputStreamReader isr = null;
        isr = new InputStreamReader(incomingData);
        BufferedReader in = new BufferedReader(isr);
        String line = null;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }

        data = sb.toString().trim();
        JSONObject jsonObject = new JSONObject(data);
        return jsonObject;
    }

    public void saveSession(String sessId, String phoneNumber, String serviceCode, String textReceived) {

        try {

            SessionTrack sessionTrack = new SessionTrack();
            sessionTrack.setSessionId(sessId);
            sessionTrack.setPhoneNumber(phoneNumber);
            sessionTrack.setServiceCode(serviceCode);
            sessionTrack.setTextReceived(textReceived);

            sessionTrackDAO.create(sessionTrack);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
