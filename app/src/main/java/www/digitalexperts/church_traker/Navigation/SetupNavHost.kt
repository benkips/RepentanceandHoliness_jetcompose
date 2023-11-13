package www.digitalexperts.church_traker.Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import www.digitalexperts.church_traker.Presentation.Dashboard.ChurchDetailScreen
import www.digitalexperts.church_traker.Presentation.Dashboard.CommonWebinfo
import www.digitalexperts.church_traker.Presentation.Dashboard.Content
import www.digitalexperts.church_traker.Presentation.Dashboard.MainDashboard
import www.digitalexperts.church_traker.Presentation.Dashboard.Pdfteachings
import www.digitalexperts.church_traker.Presentation.Dashboard.Pdfview
import www.digitalexperts.church_traker.Presentation.Dashboard.Radio
import www.digitalexperts.church_traker.Presentation.Dashboard.Teachings
import www.digitalexperts.church_traker.Presentation.Dashboard.Webinfo
import www.digitalexperts.church_traker.Util.Constants
import www.digitalexperts.church_traker.Viewmodels.Churchviewmodel
import www.digitalexperts.church_traker.Viewmodels.Contentviewmodel
import www.digitalexperts.church_traker.Viewmodels.Folderviewmodel
import www.digitalexperts.church_traker.Viewmodels.MusicViewModel
import www.digitalexperts.church_traker.Viewmodels.Pastorsviewmodel
import www.digitalexperts.church_traker.Viewmodels.Pdfviewmodel

sealed class  Screens(val routes:String){
    object  Main:Screens(routes = Constants.Screens.MAIN_SCREEN)
    object  Church_Detail:Screens(routes = Constants.Screens.CHURCH_DETAIL_SCREEN)
    object  Teachings:Screens(routes = Constants.Screens.TEACHINGS_SCREEN)
    object  Webinfo:Screens(routes = Constants.Screens.WEBVIEW_SCREEN)
    object  Radio:Screens(routes = Constants.Screens.RADIO_SCREEN)
    object  Pdfteaching:Screens(routes = Constants.Screens.MAGAZINE_SCREEN)
    object Contentscreen:Screens(routes = Constants.Screens.CONTENT_SCREEN)
}


@Composable
fun SetupNavHost(navController: NavHostController) {

    val ChurchViewModel= hiltViewModel<Churchviewmodel>()
    val PastorsViewModel= hiltViewModel<Pastorsviewmodel>()
    val folderviewmodel= hiltViewModel<Folderviewmodel>()
    val musicViewModel= hiltViewModel<MusicViewModel>()
    val pdfviewmodel= hiltViewModel<Pdfviewmodel>()
    val contentviewmodel= hiltViewModel<Contentviewmodel>()

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

            Radio( isMusicPlaying = musicViewModel.isMusicPlaying,musicViewModel,navController)
        }
        composable(route =Screens.Pdfteaching.routes ){ backStackEntry ->

            Pdfteachings(navController =navController,viewModel =pdfviewmodel)
        }

        composable(route =Screens.Contentscreen.routes ){ backStackEntry ->

            Content(navController =navController , viewModel =contentviewmodel, viewModel2 = pdfviewmodel)
        }
        composable(
            "webviews/{urls}",
            arguments = listOf(
                navArgument("urls") {
                    type = NavType.StringType
                }
            ),
        ){
                navBackStackEntry ->
            navBackStackEntry.arguments?.getString("urls")?.let { urlz ->
                CommonWebinfo(urlz)
            }
        }

        composable(
            "pdfview/{document}",
            arguments = listOf(
                navArgument("document") {
                    type = NavType.StringType
                }
            ),
        ){
                navBackStackEntry ->
            navBackStackEntry.arguments?.getString("document")?.let { document ->
                Pdfview(document)
            }
        }
    }

}