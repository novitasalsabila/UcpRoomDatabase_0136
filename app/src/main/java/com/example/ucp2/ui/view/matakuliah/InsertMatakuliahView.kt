package com.example.ucp2.ui.view.matakuliah
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.dosen.DosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.PenyediaViewModelDosen
import com.example.ucp2.ui.viewmodel.matakuliah.FormErrorState
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahEvent
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.MatkulUIState
import kotlinx.coroutines.launch

object DestinasiInsertMatkul : AlamatNavigasi{
    override val route: String = "insert_matkul"
} //object dikenal sebagai nama halamnan di insertmhsview

@Composable
fun InsertMatkulView(
    onBack:() -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatakuliahViewModel = viewModel(factory = PenyediaViewModelDosen.Factory)
){
    val uiState=viewModel.uiState //ambil ui state dari viewmodel
    val snackbarHostState = remember { SnackbarHostState() } //snackbar state
    val coroutineScope = rememberCoroutineScope()

    //observasi perubahan snackbarmassage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) //tampilansnackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ){ padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah MataKuliah",
                modifier = Modifier
            )
            //isi body
            InsertBodyMatkul(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent) //update state di viewmodel
                },
                onClick = {
                    viewModel.saveData() //simpan data
                    onNavigate()
                }
            )
        }
    }
}
