package org.mifos.mobile.ui.beneficiary_application.presentation

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.theme.MifosMobileTheme

@Composable
fun BeneficiaryTrailingIcon (
    @DrawableRes icon:Int,
    icondescription:String,
    onclick: ()->Unit
){
    IconButton(onClick = onclick ) {
        Icon(
            tint = MaterialTheme.colorScheme.onSurface,
            painter = painterResource(id = icon),
            contentDescription = icondescription,

            )

    }
}


@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun IconsPreview() {
    MifosMobileTheme {

        BeneficiaryTrailingIcon(icon = R.drawable.ic_arrow_drop_down, icondescription = "" ) {

        }

    }
}