import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "5837140993:AAFoYa2zRNq6xNJTyoOGR30SVEaPXZUxy74";

    private static final String BOT_USERNAME = "@MyBotForHomeWork_bot";

    public static final String URL = "https://api.nasa.gov/planetary/apod?api_key=9iVn7vDgpIgceULdHDn86d5cxCEQ2lDaMl5Xrmvm";

    public static long chat_id;
    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            chat_id = update.getMessage().getChatId();
            switch (update.getMessage().getText()){
                case "/help":
                    sendMessage("Привет я бот, которого создали для выполнения дз и я высылаю ссыли на картинки по запроосу.");
                    break;
                case "/givePicture":
                    try {
                        sendMessage("Всегда рад помочь!");
                        sendMessage(Utils.getUrl(URL));
                    } catch (IOException e){
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    sendMessage("Повторите пожалуйста.");
            }
        }

    }
    private void sendMessage(String MessageText){

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(MessageText);

        try {
            execute(message);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
