package edu.nighthawks.soundwave.app;

import edu.nighthawks.soundwave.contacts.Contact;
import edu.nighthawks.soundwave.http.HttpServerGetMessageCount;

/**
 * Created by joe.keefe on 10/10/2015.
 */
public class SoundWaveMessagePoller extends Thread
{
    static volatile boolean bPolling;

    public void run()
    {
        bPolling = false;

        // While one, pool server for messages
        while(SoundWaveApplication.getApplicationObject().soundWaveConfig.isPollingServerForMessages())
        {
            if (bPolling == false)
                pollServer();

            // Breathe
            SoundWaveApplication.getApplicationObject().sleep(500);
        }

    }

    private synchronized void pollServer()
    {
        boolean bNewMessage = false;
        bPolling = true;


        for (Contact contact : SoundWaveApplication.getApplicationObject().soundWaveConfig.mContactList)
        {
            HttpServerGetMessageCount getMessageCount = new HttpServerGetMessageCount(contact.getUserIdContact());
            getMessageCount.start();

            while (getMessageCount.isDone() == false)
            {}

            String reponseCount = getMessageCount.getmRawResponseBodyJson();

            if (reponseCount != null && reponseCount.isEmpty() == false)
            {
                contact.setmMessageCount(Integer.parseInt(reponseCount));
                bNewMessage = true;
            }
        }

        //JSONParser parser = new JSONParser(getMessageCount.getmRawResponseBodyJson());


         //TODO add timeouts

        if (bNewMessage) // TODO new message
        {
            // get message list

            //update contact list to show new messages

            // tell UI to refresh
            if (SoundWaveApplication.getApplicationObject().soundWaveController.mHandler != null)
            {
                // Let the UI know to refresh to load new message
                SoundWaveApplication.getApplicationObject().soundWaveController.mHandler.sendEmptyMessage(1);

            }

        }

        bPolling = false;
    }
}
