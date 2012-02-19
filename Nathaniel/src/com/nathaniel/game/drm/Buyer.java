package com.nathaniel.game.drm;

import ie.t5.drm.SDKActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Buyer {
	
	private static final String TAG = Buyer.class.getSimpleName();
	 /**
     * A function to call the authenticate purchase method and check the status
     * of the transaction.
     *
     * @param auItemID
     *            - The ID of the item used in the purchase request call.
     */
    public static void authenticatePurchase(Context context, Integer auItemID) {
        /*
           * Identifier for the authenticate purchase code ID. This has to be
           * different than the ID used for the Purchase Request code ID.
           */
        int requestCode = 2;

        /*
           * Create a new intent for the authenticate purchase activity and pass
           * the current Activity as the first parameter and the SDK's activity as
           * the second parameter
           */
        Intent i = new Intent(context, SDKActivity.class);

        i.putExtra("method", "AuthenticatePurchase"); // method to be called

        /* Parameter to be passed to the authenticatePurchase method */
        i.putExtra("itemID", auItemID);

        /*
           * Start the new activity with a request code. This request code is to
           * be used in the onActivityResult() method to act when the authenticate
           * purchase activity finishes and returns a result
           */
        ((Activity) context).startActivityForResult(i, requestCode);
    }

    /**
     * A function to call the purchase request method and initiate a purchase.
     * This function should be called from an Android activity.
     *
     * @param itemID
     *            - The ID of the item to be purchased. This has to be unique
     *            per item.
     * @param price
     *            - Price of the item to be purchased.
     * @param currency
     *            - Optional currency value (official ISO 4217 currency names).
     * @param orderTitle
     *            - The item title to be displayed to the user. This is
     *            optional.
     */
    public static void purchaseRequest(Context context, Integer itemID, Double price, String currency, String orderTitle) {
        /* Identifier for the purchase request code ID. */
        int requestCode = 1;

        /*
           * Create a new intent for the purchase request activity and pass the
           * current Activity as the first parameter and the SDK's activity as the
           * second parameter
           */
        Intent i = new Intent(context, SDKActivity.class);

        i.putExtra("method", "PurchaseRequest");// method to be called

        /* Parameters to be passed to the purchaseRequest method */
        i.putExtra("itemID", itemID);
        i.putExtra("price", "4.99");
        i.putExtra("currency", currency);
        i.putExtra("orderTitle", orderTitle);

        /*
           * Start the new activity with a request code. This request code is to
           * be used in the onActivityResult() method to act when the purchase
           * request activity finishes and returns a result
           */
        ((Activity) context).startActivityForResult(i, requestCode);
    }

   
    
    
    public static PurchaseStatus result(Context context, int requestCode, int resultCode){
        switch (requestCode) {
            case 1:/*
				 * <- This request code has to be consistent with the Purchase
				 * request code ID provided in PurchaseRequestInterface()
				 */

                Log.d(TAG, "Purchase Request result is: " + resultCode);
                if (resultCode == 2) {
                    /* The Purchase have been successful. */

                   return PurchaseStatus.SUCCESSFUL;

                } else if (resultCode == 1 || resultCode == 0) {
                    /* The Purchase is still processing. */

                	return PurchaseStatus.PROCESSING;

                } else if (resultCode < 0) {
                    /* The purchase is NOT successful. */

                	return PurchaseStatus.FAILED;
                }

                break;

            case 2:/*
				 * <- This request code has to be consistent with the Purchase
				 * request code ID provided in AuthenticatePurchaseInterface()
				 */
                Log.d(TAG, "Authenticate purchase result is: " + resultCode);

                if (resultCode == 2) {
                    /* The Purchase have been successful. */

                	return PurchaseStatus.SUCCESSFUL;

                } else if (resultCode == 1 || resultCode == 0) {
                    /* The Purchase is still processing. */

                	return PurchaseStatus.PROCESSING;

                } else if (resultCode < 0) {
                    /* The purchase is NOT successful. */

                	return PurchaseStatus.FAILED;
                }

                break;
        }
        return PurchaseStatus.FAILED;
    }

}
