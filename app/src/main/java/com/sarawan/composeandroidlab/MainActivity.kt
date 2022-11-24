package com.sarawan.composeandroidlab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarawan.composeandroidlab.ui.theme.ComposeAndroidLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAndroidLabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
@Composable
private fun MyApp(
    modifier: Modifier = Modifier
) {
    var shouldShowOnboarding by rememberSaveable {
        mutableStateOf(true)
    }
    Surface(modifier = modifier) {
        if(shouldShowOnboarding) {
            OnboardingScreen({
                shouldShowOnboarding = false
            })
        } else {
            Greetings()
        }
    }
}
@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 7.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}
@Composable
fun Greeting(name: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}
@Composable
fun CardContent(name : String) {
    var expanded by rememberSaveable  { mutableStateOf(false) }

    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(vertical = 7.dp, horizontal = 14.dp)
            .clip(MaterialTheme.shapes.medium)
            .shadow(8.dp)) {
        //vertical top to down
        Row(modifier = Modifier.padding(24.dp)
            .animateContentSize(animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ))) {
            Column(modifier = Modifier
                .weight(1f)) {
                Text(text = "Hello")
                Text(text = "$name", style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
                if(expanded) {
                    Text(text = stringResource(R.string.default_desctiption))
                }
            }
            IconButton(onClick = {
                expanded = !expanded
            }) {
            }
            Icon(
                if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription =
                if (expanded) stringResource(R.string.show_less)
                else stringResource(R.string.show_more)
            )
        }
    }
}
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(onClick = onContinueClicked,
            modifier = Modifier.padding(vertical = 24.dp)) {
            Text("Continue")
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeAndroidLabTheme {
        MyApp()
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
private fun GreetingsPreview() {
    ComposeAndroidLabTheme() {
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ComposeAndroidLabTheme() {
        OnboardingScreen(onContinueClicked = {})
    }
}