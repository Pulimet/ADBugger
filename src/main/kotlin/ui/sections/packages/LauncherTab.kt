package ui.sections.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore

@Composable
fun LauncherTab(model: AppStore = koinInject()) {

    Column(modifier = Modifier.fillMaxSize()) {

    }

}

// adb shell am start -a android.intent.action.VIEW -c android.intent.category.DEFAULT -e foo bar -e bert ernie -n my.package.component.blah
// adb shell am start -n com.example.jk.checkwifi/.MainActivity -e "imei" $myimei -e "ip" $IP

/*
The available options related to extras are:

-e | --es extra_key extra_string_value: Add string data as a key-value pair.
--ez extra_key extra_boolean_value: Add boolean data as a key-value pair.
--ei extra_key extra_int_value: Add integer data as a key-value pair.
--el extra_key extra_long_value: Add long data as a key-value pair.
--ef extra_key extra_float_value: Add float data as a key-value pair.
--eu extra_key extra_uri_value: Add URI data as a key-value pair.
--ecn extra_key extra_component_name_value: Add a component name, which is converted and passed as a ComponentName object.
--eia extra_key extra_int_value[,extra_int_value...]: Add an array of integers.
--ela extra_key extra_long_value[,extra_long_value...]: Add an array of longs.
--efa extra_key extra_float_value[,extra_float_value...]: Add an array of floats.
 */