package mgpt.terminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 */
public class sendAndReceive {
    // Var to hold AI's response
    private String receivedResponse;
    
    /**
     * Takes in message and sends it off to the AI to get
     * a response back.
     * @param message
     */
    public void sendMessage (String message) {
        // Wrap in try{} and catch{}
        try {
            // Creates var to hold command, starts processBuilder to send message
            String commandToSend = "apfel '" + message + "'";
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", commandToSend);
            
            // Some stuff
            processBuilder.redirectErrorStream(true); 
            Process process = processBuilder.start();
            process.getOutputStream().close();

            // Some other stuff
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            // Some other stuff
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Adds received response and puts it to var
            int exitCode = process.waitFor();
            receivedResponse = output.toString();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns AI's response.
     * @return receivedResponse
     */
    public String getReceivedResponse() {
        return receivedResponse;
    }
}
