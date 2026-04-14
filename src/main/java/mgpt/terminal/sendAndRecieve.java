package mgpt.terminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class sendAndRecieve {
    private String receivedResponse;
    
    public void sendMessage (String message) {
        try {
            String commandToSend = "apfel '" + message + "'";
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", commandToSend);
            
            processBuilder.redirectErrorStream(true); 
            Process process = processBuilder.start();
            process.getOutputStream().close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            receivedResponse = output.toString();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getReceivedResponse() {
        return receivedResponse;
    }
}
