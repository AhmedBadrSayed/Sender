package com.projects.ahmedbadr.sender.Service;

import android.location.Location;
import android.util.Log;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;

/**
 * Created by Ahmed Badr on 19/10/2016.
 */
public class LocationSender {

    PubNub pubnub;

    public LocationSender(){
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-4a62d6a8-9515-11e6-82f8-02ee2ddab7fe");
        pnConfiguration.setPublishKey("pub-c-4194e9ae-c331-46a8-8188-dfb7b1498cea");
        pubnub = new PubNub(pnConfiguration);
    }

    public void subscribeChannel(final Location mCurrentLocation){
        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {

                if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                    Log.d("Error ","connectivity is lost");
                }

                else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {

                    if (status.getCategory() == PNStatusCategory.PNConnectedCategory){
                        pubnub.publish().channel("Track").message(mCurrentLocation).async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                if (!status.isError()) {
                                    // Message successfully published to specified channel.
                                    Log.d("Success ","Done");
                                }
                                else {
                                    Log.d("Error ", String.valueOf(status.getErrorData()));
                                }
                            }
                        });
                    }
                }
                else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {
                    Log.d("Error ","connectivity is lost");
                }
                else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {

                }
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                if (message.getChannel() != null) {
                    // Message has been received on channel group stored in
                    // message.getChannel()
                    Log.d("Channel ",message.getChannel());
                }
                else {
                    Log.d("Subscrip ",message.getSubscription());
                }

                String msg = String.valueOf(message.getMessage());
                Log.d("msg ",msg);
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });

        pubnub.subscribe().channels(Arrays.asList("Track")).execute();
    }
}
