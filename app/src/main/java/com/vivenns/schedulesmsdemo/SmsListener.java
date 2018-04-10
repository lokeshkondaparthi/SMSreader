package com.vivenns.schedulesmsdemo;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.vivenns.schedulesmsdemo.services.TTSService;
import com.vivenns.schedulesmsdemo.services.TTSServiceOld;

public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "Got new message from", Toast.LENGTH_SHORT).show();

            if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
                Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
                SmsMessage[] msgs = null;
                String msg_from;
                if (bundle != null){
                    //---retrieve the SMS message received---
                    try{
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for(int i=0; i<msgs.length; i++){
                            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                            msg_from = msgs[i].getOriginatingAddress();
                            String msgBody = msgs[i].getMessageBody();
                            Intent intent1 = new Intent(context, TTSService.class);
                            String value =getContactName(context, msg_from) ;
                            String readIt = "";
                            if(value == null)
                                readIt = "Message from unknown member ";
                            else
                                readIt = "Message from  "+value;
                            intent1.putExtra("text", readIt);
                            context.startService(intent1);
                        }
                    }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                    }
                }
            }

        }
    }
    public  String getContactName(Context context, String phoneNumber) {
        String contactName = null;
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));

        // querying contact data store
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();

        Log.d("SmsListener", "Contact Name: " + contactName);
        return contactName;
    }
}