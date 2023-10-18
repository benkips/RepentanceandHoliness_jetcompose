package www.digitalexperts.church_tracker.Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import www.digitalexperts.church_tracker.Presentation.Dashboard.ChurchDetailScreen
import www.digitalexperts.church_tracker.Presentation.Dashboard.MainDashboard
import www.digitalexperts.church_tracker.Presentation.Dashboard.Radio
import www.digitalexperts.church_tracker.Presentation.Dashboard.Teachings
import www.digitalexperts.church_tracker.Presentation.Dashboard.Webinfo
import www.digitalexperts.church_tracker.Util.Constants
import www.digitalexperts.church_tracker.Viewmodels.Churchviewmodel
import www.digitalexperts.church_tracker.Viewmodels.Folderviewmodel
import www.digitalexperts.church_tracker.Viewmodels.Pastorsviewmodel

sealed class  Screens(val routes:String){
    object  Main:Screens(routes = Constants.Screens.MAIN_SCREEN)
    object  Church_Detail:Screens(routes = Constants.Screens.CHURCH_DETAIL_SCREEN)
    object  Teachings:Screens(routes = Constants.Screens.TEACHINGS_SCREEN)
    object  Webinfo:Screens(routes = Constants.Screens.WEBVIEW_SCREEN)
    object  Radio:Screens(routes = Constants.Screens.RADIO_SCREEN)
}


@Composable
fun SetupNavHost(navController: NavHostController) {

    val ChurchViewModel= hiltViewModel<Churchviewmodel>()
    val PastorsViewModel= hiltViewModel<Pastorsviewmodel>()
    val folderviewmodel= hiltViewModel<Folderviewmodel>()

    NavHost(navController =navController,
       startDestination = Screens.Main.routes){


        composable(route =Screens.Main.routes ){

            MainDashboard(navController =navController , viewModel =ChurchViewModel )
        }

        composable(route =Screens.Church_Detail.routes  + "/{id}"){ backStackEntry ->

            ChurchDetailScreen(navController =navController , viewModel =ChurchViewModel, viewModel2 = PastorsViewModel)
        }
        composable(route =Screens.Teachings.routes ){ backStackEntry ->

            Teachings(navController =navController , viewModel =folderviewmodel)
        }
        composable(route =Screens.Webinfo.routes ){ backStackEntry ->

            Webinfo( viewModel =folderviewmodel)
        }
        composable(route =Screens.Radio.routes ){ backStackEntry ->

            Radio()
        }
    }
}