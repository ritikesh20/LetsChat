package com.example.wechat.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wechat.R


@Preview
@Composable
fun CallHistory() {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 10.dp)
        ) {

            Text(text = "Calls", fontWeight = FontWeight.Bold, fontSize = 32.sp)

            Text(text = "Recent", fontWeight = FontWeight.Bold, fontSize = 14.sp)

            LazyColumn {
                items(20) {
                    CallView()
                }
            }
        }
    }
}


@Composable
fun CallView() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .border(1.dp, color = Color.Gray, shape = CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.imagedp), contentDescription = "",
                modifier = Modifier.clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(20.dp))

        Column {
            Text(text = "Mahesh Dala", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))

            Row() {
                Image(
                    painter = painterResource(id = R.drawable.cemara), contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )

                Text(text = "Outgoing Call", modifier = Modifier.padding(5.dp))
            }

        }
        Spacer(modifier = Modifier.width(60.dp))
        Text(
            text = "Monday", color = Color.Gray,
            modifier = Modifier.padding(top = 20.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Image(
            painter = painterResource(id = R.drawable.cyclone), contentDescription = "Details",
            modifier = Modifier.padding(top = 20.dp)
        )

    }
    HorizontalDivider()
}

