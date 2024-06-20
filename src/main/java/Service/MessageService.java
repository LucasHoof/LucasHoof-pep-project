package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessages(int account_id){
        return messageDAO.getAllMessages(account_id);
    }

    public Message createMessage(Message message){
        if(message.getMessage_text().length() < 255 && message.getMessage_text() != ""){
            return messageDAO.createMessage(message);
        }
        else{
            return null;
        }
    }

    public Message getMessageByID(int message_id){
        return messageDAO.getMessageByID(message_id);
    }

    public Message updateMessageByID(int message_id, String message_text){
        if(message_text != "" && message_text.length() < 255){
            if(getMessageByID(message_id) != null){
                messageDAO.updateMessageByID(message_id, message_text);
                return getMessageByID(message_id);
            }
        }
        return null;
    }

    public Message deleteMessageByID(int message_id){
        Message deleted = getMessageByID(message_id)
        if(deleted != null){
            messageDAO.deleteMessageByID(message_id);
            return deleted;
        }
        return null;
    }
}
