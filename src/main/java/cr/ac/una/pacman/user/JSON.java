package cr.ac.una.pacman.user;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class JSON {
    private static JSON INSTANCE = null;
    private String JSONFILE = "../Pacman/src/main/java/cr/ac/una/pacman/user/User.json";
    private ObjectMapper objectMapper;
    private File jsonFile;
    private JsonNode jsonNode;

    public JSON() {
        this.objectMapper = new ObjectMapper();
        this.jsonFile = new File(JSONFILE);
        cargarJson();
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (JSON.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JSON();
                }
            }
        }
    }

    public static JSON getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    private void cargarJson() {
        try {
            jsonNode = objectMapper.readTree(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modificarVariable(String clave, String nuevoValor) {
        ((ObjectNode) jsonNode).put(clave, nuevoValor);
    }
    public void modificarVariable(String clave, int nuevoValor) {
        ((ObjectNode) jsonNode).put(clave, nuevoValor);

    }
    public void modificarVariable(String clave, boolean nuevoValor) {
        ((ObjectNode) jsonNode).put(clave, nuevoValor);

    }

    public void modificarVariable(String clave, double nuevoValor) {
        ((ObjectNode) jsonNode).put(clave, nuevoValor);
    }
    public String obtenerValor(String clave) {
        return jsonNode.get(clave).asText();
    }
    public Boolean obtenerValorBoolean(String clave) {
        return jsonNode.get(clave).asBoolean();
    }
    public int obtenerValorInteger(String clave) {
        return jsonNode.get(clave).asInt();
    }
    
    public double obtenerValorDouble(String clave) {
        return jsonNode.get(clave).asDouble();
    }

    public void guardarCambios() {
        try {
            objectMapper.writeValue(jsonFile, jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
