package com.projetolivro.junior_carvalho.carros.activity.ActivityUtils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by Junior_Carvalho on 07/05/2016.
 */
public class IntentUtils {


    public static boolean isAvailable(Context ctx, Intent intent){

        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list = mgr.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() >0;
    }

}

