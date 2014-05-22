package treeset.extensions.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by daemontus on 06/04/14.
 */
public class IntentUtils {

    public static boolean shareImageSpecific(String packageFragment, String subject, String text, String filePath, Context ctx) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/jpeg");

        // gets the list of intents that can be loaded.
        PackageManager manager = ctx.getPackageManager();

        if (manager == null) {
            return false;
        }

        List<ResolveInfo> resInfo = manager.queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()){
            for (ResolveInfo info : resInfo) {
                ActivityInfo activityInfo = info.activityInfo;
                if (activityInfo == null) {
                    continue;
                }
                if (activityInfo.packageName.toLowerCase().contains(packageFragment)) {
                    share.putExtra(Intent.EXTRA_SUBJECT,  subject);
                    share.putExtra(Intent.EXTRA_TEXT,     text);
                    share.putExtra(Intent.EXTRA_TITLE,  subject);
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)) ); // Optional, just if you wanna share an image.
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found){
                return false;
            }

            ctx.startActivity(Intent.createChooser(share, "Select"));
            return true;
        }
        return false;
    }

    public static void shareImage(String subject, String text, String filePath, Context ctx) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)) ); // Optional, just if you wanna share an image.
        share.putExtra(Intent.EXTRA_TEXT,     text);
        share.putExtra(Intent.EXTRA_SUBJECT,  subject);
        ctx.startActivity(Intent.createChooser(share, "Select"));
    }

}
