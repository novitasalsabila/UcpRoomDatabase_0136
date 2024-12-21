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
@Composable
fun InsertBodyMatkul(
    modifier: Modifier = Modifier,
    onValueChange: (MatakuliahEvent) -> Unit,
    uiState: MatkulUIState,
    onClick: () -> Unit
){
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMatkul(
            matkulEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick=onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormMatkul(
    matkulEvent: MatakuliahEvent = MatakuliahEvent(),
    onValueChange : (MatakuliahEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    DosenViewModel: DosenViewModel = viewModel(factory = PenyediaViewModelDosen.Factory)
){
    val DosenUIState by DosenViewModel.DosenUIState.collectAsState()
    val listDosen = DosenUIState.listDosen.map{it.nama}
    val semester = listOf("Genap","Ganjil")
    val jenis = listOf("Wajib","Tidak")

    Column (modifier = modifier.fillMaxWidth())
    {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.kode,
            onValueChange = {
                onValueChange(matkulEvent.copy(kode = it))
            },
            label = { Text("KODE") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukan KODE") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.nama,
            onValueChange = {
                onValueChange(matkulEvent.copy(nama = it))
            },
            label = { Text("Nama")},
            isError = errorState.nama != null,
            placeholder = { Text("Masukan nama")},
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.sks,
            onValueChange = {
                onValueChange(matkulEvent.copy(sks = it))
            },
            label = { Text("sks")},
            isError = errorState.sks != null,
            placeholder = { Text("Masukan SKS")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.sks ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Semester")
        Row (modifier = Modifier.fillMaxWidth()
        ){
            semester.forEach{sem ->
                Row  (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = matkulEvent.semester == sem,
                        onClick = {
                            onValueChange(matkulEvent.copy(semester = sem))
                        },
                    )
                    Text(
                        text = sem,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Matakuliah")
        Row (modifier = Modifier.fillMaxWidth()
        ){
            jenis.forEach{jk ->
                Row  (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = matkulEvent.jenisMatkul == jk,
                        onClick = {
                            onValueChange(matkulEvent.copy(jenisMatkul = jk))
                        },
                    )
                    Text(
                        text = jk,
                    )
                }
            }
        }

        DropdownMenuField(
            label = "Nama Dosen Pengampu",
            options = listDosen,
            selectedOption = matkulEvent.dosenPengampu,
            onOptionSelected = {
                    selectedDosen -> onValueChange(matkulEvent.copy(dosenPengampu = selectedDosen))
            },
            isError = errorState.dosenPengampu != null,
            errorMessage = errorState.dosenPengampu
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelection by remember { mutableStateOf(selectedOption) }


    Column {
        OutlinedTextField (
            value = currentSelection,
            onValueChange = {}, // Tidak memungkinkan input manual
            readOnly = true,
            label = { Text (label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        currentSelection = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}