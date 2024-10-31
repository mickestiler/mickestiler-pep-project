package Controller;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Message;
import Model.Account;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesGivenAccountIdHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount==null){
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.login(account);
        if (loggedInAccount==null){
            ctx.status(401);
        } else {
            ctx.json(loggedInAccount);
        }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(createdMessage);
        }
    }
    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageByIdHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message getMessage = messageService.getMessageById(message_id);
        if (getMessage != null) {
            ctx.json(getMessage);
        }
    }

    private void deleteMessageByIdHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleteMessage = messageService.deleteMessageById(message_id);
        if (deleteMessage != null) {
            ctx.json(deleteMessage);
        }
    }

    /**
     * Controller handler to update message given a message_id and message_text.
     * @param ctx
     */
    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessageById(message_id, message.getMessage_text());
        if (updatedMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(updatedMessage);
        }
    }

    private void getAllMessagesGivenAccountIdHandler(Context ctx) {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> listAllMessagesByUser = messageService.getAllMessagesByUser(account_id);
        if (listAllMessagesByUser != null) {
            ctx.json(listAllMessagesByUser);
        } else {
            ctx.json(Collections.emptyList());
        }
    }
}