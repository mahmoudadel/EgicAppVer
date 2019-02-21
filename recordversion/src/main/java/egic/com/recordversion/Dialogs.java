package egic.com.recordversion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class Dialogs {

    public static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongtToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static ProgressDialog getProgressDialog(Activity context,
                                                   String message, boolean cancelable,int style) {
        ProgressDialog pDialog = new ProgressDialog(context, style);
        pDialog.setMessage(message);
        pDialog.setCanceledOnTouchOutside(cancelable);
        pDialog.setCancelable(cancelable);
        return pDialog;
    }

    public static ProgressDialog getProgressDialog(Activity context,
                                                   String message, boolean cancelable) {
        ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage(message);
        pDialog.setCanceledOnTouchOutside(cancelable);
        pDialog.setCancelable(cancelable);
        return pDialog;
    }

    public static ArrayAdapter<String> showProgressDialogWithList(
            Activity context,
            String title,
            String[] list,
            boolean cancelable,
            DialogInterface.OnClickListener listener) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title)
                .setAdapter(adapter, listener)
                .setCancelable(cancelable);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.show();
        return adapter;
    }

    public static AlertDialog showProgressDialogWithCheckboxList(
            Activity context,
            String title,
            String[] list,
            int selected,
            boolean cancelable,
            DialogInterface.OnClickListener listener) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title)
                .setSingleChoiceItems(list, selected, listener)
                .setCancelable(cancelable);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showProgressDialogWithCheckboxListMultiChoice(
            Activity context,
            String title,
            String[] list,
            boolean[] selected,
            boolean cancelable,
            String positive,
            String negative,
            DialogInterface.OnClickListener positiveClick,
            DialogInterface.OnClickListener negativeClick,
            DialogInterface.OnMultiChoiceClickListener listener) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title)
                .setMultiChoiceItems(list, selected, listener)
                .setCancelable(cancelable)
                .setPositiveButton(positive, positiveClick)
                .setNegativeButton(negative, negativeClick);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.show();
        return alertDialog;
    }

    public static ArrayAdapter<String> showProgressDialogWithList(
            Activity context,
            String title,
            List<String> list,
            boolean cancelable,
            DialogInterface.OnClickListener listener,
            DialogInterface.OnDismissListener dismissListener) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title)
                .setAdapter(adapter, listener)
                .setCancelable(cancelable);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.setOnDismissListener(dismissListener);
        alertDialog.show();
        return adapter;
    }

    public static ArrayAdapter showProgressDialogWithList(
            Activity context,
            String title,
            List<Object> list,
            final String propertyName,
            boolean cancelable,
            DialogInterface.OnClickListener listener,
            DialogInterface.OnDismissListener dismissListener) {


        ArrayAdapter adapter = new ArrayAdapter<Object>(context, android.R.layout.simple_list_item_1, list) {
            @Override
            public Object getItem(int position) {
                String[] names = propertyName.split(",");
                List<Object> values = new ArrayList<>();
                for (int i = 0; i < names.length; i++) {
                    Object value = getValueFromObject(super.getItem(position), names[i]);
                    values.add(value);
                }
                return TextUtils.join("\n", values);
            }
        };
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title)
                .setAdapter(adapter, listener)
                .setCancelable(cancelable);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.setOnDismissListener(dismissListener);
        alertDialog.show();
        return adapter;
    }

    public static AlertDialog showProgressDialogWithEditText(
            Activity context,
            String title,
            String message,
            EditText edit,
            boolean cancelable,
            String okText,
            String cancelText,
            final Runnable okMethod,
            final Runnable cancelMethod) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 8, 8, 8);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);


        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 4, 0, 0);
        edit.setLayoutParams(params);

        layout.addView(edit);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setView(layout);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(okText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (okMethod != null)
                    okMethod.run();
            }
        });
        // set negative button: No message
        alertDialogBuilder.setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (cancelMethod != null)
                    cancelMethod.run();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.setCancelable(cancelable).create();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.show();
        return alertDialog;
    }
    public static AlertDialog showProgressDialogWithEditTextWithNoTitle(
            Activity context,
            String message,
            EditText edit,
            boolean cancelable,
            String okText,
            String cancelText,
            final Runnable okMethod,
            final Runnable cancelMethod) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 8, 8, 8);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);


        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        edit.setLayoutParams(params);
        layout.addView(edit);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setView(layout);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(okText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (okMethod != null)
                    okMethod.run();
            }
        });
        // set negative button: No message
        alertDialogBuilder.setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (cancelMethod != null)
                    cancelMethod.run();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
       // alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCanceledOnTouchOutside(cancelable);

        alertDialog.show();
        return alertDialog;
    }

    public static void showProgressDialogWithTwoButtons(
            Activity context,
            String title,
            String message,
            boolean cancelable,
            String okText,
            String cancelText,
            final Runnable okMethod,
            final Runnable cancelMethod) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(okText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                if (okMethod != null)
                    okMethod.run();
            }
        });
        // set negative button: No message
        alertDialogBuilder.setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                if (cancelMethod != null)
                    cancelMethod.run();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.setCancelable(cancelable).create();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.show();
    }

    public static void showProgressDialogWithOneButton(
            Activity context,
            String title,
            String message,
            boolean cancelable,
            String okText,
            final Runnable okMethod) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(okText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                if (okMethod != null)
                    okMethod.run();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.setCancelable(cancelable).create();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.show();
    }

    private static Object getValueFromObject(Object obj, String propertyName) {

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields)
            try {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(obj);
                if (key.equalsIgnoreCase(propertyName)) {
                    return value;
                }
            } catch (IllegalAccessException e) {
            }
        return null;
    }
}
