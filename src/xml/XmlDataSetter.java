package xml;

import controller.workspace.WorkspaceLoadPreferences;
import dataStorage.*;
import exceptions.XmlFormatException;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Given a map from a parsed XML of data, use it to set the required variables. Due to the structure of the
 * classes being saved to, this process is very heavily manual.
 */

class XmlDataSetter implements IXmlStrings{

    VariableStorage setValueVariables(Map<String, Map<String, String>> valueVariableMap) throws XmlFormatException{
        VariableStorage newValueVariableStorage = new VariableStorage();
        if(valueVariableMap.isEmpty()) {
            return newValueVariableStorage;
        }

        for(String oneVariableKey : valueVariableMap.keySet()) {
            Map<String, String> oneValueVariableMap = valueVariableMap.get(oneVariableKey);
            String name;
            try {
                name = oneValueVariableMap.get(VARIABLE_NAME);
            }
            catch (NullPointerException e) {
                throw new XmlFormatException(VARIABLE_NAME);
            }

            String value;

            try {
                value = oneValueVariableMap.get(VARIABLE_VALUE);
            }
            catch(NullPointerException e) {
                throw new XmlFormatException(VARIABLE_VALUE);
            }

            newValueVariableStorage.setVariable(
                    name,
                    Double.parseDouble(value)
            );
        }

        return newValueVariableStorage;
    }

    CommandStorage setCommandVariables(Map<String, Map<String, String>> commandVariableMap) throws XmlFormatException {
        CommandStorage newCommandVariableStorage = new CommandStorage();
        if(commandVariableMap.isEmpty()) {
            return newCommandVariableStorage;
        }

        for(String oneVariableKey : commandVariableMap.keySet()) {
            Map<String, String> oneCommandVariableMap = commandVariableMap.get(oneVariableKey);

            List<String> parameterList = new ArrayList<>();
            try {
                String stringOfParams = oneCommandVariableMap.get(FUNCTION_PARAMETERS);
                if(!(stringOfParams.equals(EMPTY))) {
                    parameterList = Arrays.asList(oneCommandVariableMap.get(FUNCTION_PARAMETERS).split(SPACE));
                }
            }
            catch (NullPointerException e) {
                throw new XmlFormatException(FUNCTION_PARAMETERS);
            }

            String functionName;
            try{
                functionName = oneCommandVariableMap.get(FUNCTION_NAME);
            }
            catch(NullPointerException e) {
                throw new XmlFormatException(FUNCTION_NAME);
            }

            String functionBody;
            try{
                functionBody = oneCommandVariableMap.get(FUNCTION_BODY);
            }
            catch(NullPointerException e) {
                throw new XmlFormatException(FUNCTION_BODY);
            }

            newCommandVariableStorage.setCommand(
                    functionName,
                    parameterList,
                    functionBody
            );

        }
        return newCommandVariableStorage;
    }

    WorkspaceLoadPreferences setWorkspaceLoadPreferences(Map<String, Object> workspaceMap) throws XmlFormatException {
        List<Integer> backgroundColor = new ArrayList<>();
        List<Integer> lineColor = new ArrayList<>();

        Map<String, String> colorMap = new HashMap<>();
        try {
            colorMap = (Map<String, String>) workspaceMap.get(BACKGROUND_COLOR);
        }
        catch (Exception e) {
            throw new XmlFormatException(BACKGROUND_COLOR);
        }

        for(String colorKey : colorMap.keySet()){
            backgroundColor.add(Integer.parseInt(colorMap.get(colorKey)));
        }

        Map<String, String> lineColorMap = new HashMap<>();

        try {
            lineColorMap = (Map<String, String>) workspaceMap.get(LINE_COLOR);
        }
        catch(Exception e) {
            throw new XmlFormatException(LINE_COLOR);
        }

        for(String colorKey : lineColorMap.keySet()){
            lineColor.add(Integer.parseInt(lineColorMap.get(colorKey)));
        }

        String startingImage;
        String commandLanguage;

        try {
            startingImage = (String) workspaceMap.get(STARTING_IMAGE);
        }
        catch(NullPointerException e) {
            throw new XmlFormatException(STARTING_IMAGE);
        }
        try {
            commandLanguage = (String) workspaceMap.get(COMMAND_LANGUAGE);
        }
        catch(NullPointerException e) {
            throw new XmlFormatException(COMMAND_LANGUAGE);
        }
        return new WorkspaceLoadPreferences(backgroundColor, lineColor, startingImage, commandLanguage);
    }
}
