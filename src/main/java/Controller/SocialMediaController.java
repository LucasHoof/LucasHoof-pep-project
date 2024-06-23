package Controller;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        app.post("/messages", this::createMessageHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesHandlerID);
        
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);

        //app.start(8080);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.Registration(account);

        if(addedAccount==null){
            context.status(400);
        }else{
            context.status(200);
            context.json(mapper.writeValueAsString(addedAccount));
        }

    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.Login(account);

        if(addedAccount==null){
            context.status(401);
        }else{
            context.status(200);
            context.json(mapper.writeValueAsString(addedAccount));
        }
    }

    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);

        if(addedMessage==null){
            context.status(400);
        }else{
            context.status(200);
            context.json(mapper.writeValueAsString(addedMessage));
        }
    }

    private void getMessageHandler(Context context) {
        Message message = messageService.getMessageByID(Integer.valueOf(context.pathParam("message_id")));
        if ( message != null){
            context.json(message);
        }
    }

    private void getMessagesHandler(Context context) {
        context.json(messageService.getAllMessages());
    }

    private void getMessagesHandlerID(Context context) {
        List<Message> messages = messageService.getAllMessages(Integer.valueOf(context.pathParam("account_id")));
        if ( messages != null){
            context.json(messages);
        }
    }

    private void deleteMessageHandler(Context context) {
        Message message = messageService.deleteMessageByID(Integer.valueOf(context.pathParam("message_id")));
        if ( message != null){
            context.json(message);
        }
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(context.body());

        Message addedMessage = messageService.updateMessageByID(Integer.valueOf(context.pathParam("message_id")), json.get("message_text").asText());

        if(addedMessage==null){
            context.status(400);
        }else{
            context.status(200);
            context.json(mapper.writeValueAsString(addedMessage));
        }
    }

}