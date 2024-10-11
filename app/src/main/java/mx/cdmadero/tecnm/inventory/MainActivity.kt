package mx.cdmadero.tecnm.inventory

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.zxing.integration.android.IntentIntegrator
import mx.cdmadero.tecnm.inventory.ui.theme.InventoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventoryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        this
                    )
                }
            }
        }
    }

    override protected fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        // if the intentResult is null then
        // toast a message as "cancelled"
        println("TEST resultado:")
        if (intentResult != null) {
            if (intentResult.contents == null) {
                println("TEST cancelled")
                Toast.makeText(applicationContext, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                //messageText.setText(intentResult.contents)
                //messageFormat.setText(intentResult.formatName)
                //result = intentResult.contents
                qrText = intentResult.contents
                println("TEST ${intentResult.contents}")
                println("TEST ${intentResult.formatName}")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
            println("TEST nulo")
        }
    }

}

    var qrText : String = ""
    

@Composable
fun MainScreen(modifier: Modifier = Modifier, activity: MainActivity) {
    val qrTextState by remember{ mutableStateOf(qrText) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lectura de código QR",
            style = typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(qrTextState)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            val intentIntegrator = IntentIntegrator(activity)
            intentIntegrator.setPrompt("Scan a barcode or QR Code")
            intentIntegrator.setOrientationLocked(true)
            intentIntegrator.initiateScan()
        }) {
            Text(text = "Leer QR")
        }
    }
    

}
