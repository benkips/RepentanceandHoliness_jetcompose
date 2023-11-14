package www.digitalexperts.church_traker.Data.models

import androidx.compose.ui.graphics.vector.ImageVector

data class SideNavigationItem(
    val title:String,
    val selectedIcon:ImageVector,
    val unselectedIcon:ImageVector,
    val hasNews:Boolean,
    val badgeCount:Int?=null

)
