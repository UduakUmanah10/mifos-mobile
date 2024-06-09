package org.mifos.mobile.ui.beneficiary_application.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.component.MifosOutlinedTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MifosDropdonmenu() {

    val listofdropdownitems = listOf("first", "second", "third", "fourth")

    var isexpanded by remember {
        mutableStateOf(false)
    }

    val defaultselected = stringResource(id = R.string.select_account_type)

    var selectedchoice by remember {
        mutableStateOf(defaultselected)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)

    ) {

        ExposedDropdownMenuBox(
            expanded = isexpanded,
            onExpandedChange = { isexpanded = !isexpanded },
        ) {


            MifosOutlinedTextField(
                value = TextFieldValue(selectedchoice),
                onValueChange = {},

                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isexpanded)
                },
                supportingText = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                label = R.string.add_guarantor

            )

            //TextField(modifier = Modifier.fillMaxWidth().menuAnchor(),
            //  value = selectedchoice,
            //onValueChange = {},
            //readOnly = true,
            // trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isexpanded) })

            ExposedDropdownMenu(expanded = isexpanded, onDismissRequest = { isexpanded = false }) {

                listofdropdownitems.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = {

                            //Text(text = text, color = Color.Blue)
                            Text(text = text, )

                               },
                        onClick = {
                            selectedchoice = listofdropdownitems[index]
                            isexpanded = false
                        },
                    )

                }

            }


        }
    }
}

