package es.example.ale.pr002;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private EditText cantidadTxt;
    private RadioGroup groupBtnOrigin, groupBtnChange;
    private RadioButton dollarOrigin, euroOrigin, yenOrigin, dollarChange, euroChange, yenChange;
    private ImageView imagenMonedaOrigen, imagenMonedaCambio;
    // LAS VISTAS QUE SÓLO SE USEN EN UN ÚNICO MÉTODO PUEDES DEFINIRLAS COMO LOCALES A ESE MÉTODO.
    private Button btnExange;
    private int idOrigen, idCambio;
    private double cantidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        idOrigen = disableBtnOrigen();
        idCambio = disableBtnChange();
    }

    private void initViews() {
        cantidadTxt = ActivityCompat.requireViewById(this, R.id.txtAmount);
        groupBtnOrigin = ActivityCompat.requireViewById(this, R.id.groupOrigin);
        groupBtnChange = ActivityCompat.requireViewById(this, R.id.groupChange);
        dollarOrigin = ActivityCompat.requireViewById(this, R.id.rbFromDollar);
        dollarChange = ActivityCompat.requireViewById(this, R.id.rbToDollar);
        euroOrigin = ActivityCompat.requireViewById(this, R.id.rbFromEuro);
        euroChange = ActivityCompat.requireViewById(this, R.id.rbToEuro);
        yenOrigin = ActivityCompat.requireViewById(this, R.id.rbFromPound);
        yenChange = ActivityCompat.requireViewById(this, R.id.rbToPound);
        imagenMonedaCambio = ActivityCompat.requireViewById(this, R.id.imgTo);
        imagenMonedaOrigen = ActivityCompat.requireViewById(this, R.id.imgFrom);
        btnExange = ActivityCompat.requireViewById(this,R.id.btnExchange);



        groupBtnOrigin.setOnCheckedChangeListener(this);
        groupBtnChange.setOnCheckedChangeListener(this);
        btnExange.setOnClickListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group.getId() == groupBtnOrigin.getId())
            idOrigen = disableBtnOrigen();
        else if(group.getId() == groupBtnChange.getId())
           idCambio = disableBtnChange();
    }

    @Override
    public void onClick(View v) {
        final double dolar_euro = 0.86, dolar_yen = 113.69, euro_yen = 132.12;
        double cantidad_pasada = 0;

        // EL MÉTODO parseDouble PUEDE PRODUCIR LA EXCEPCIÓN NumberFormatException
        // PRUEBA A DEJAR EN BLANCO EL EDITTEXT...
        cantidad = Double.parseDouble(String.valueOf(cantidadTxt.getText()));

        // HAZ Code -> Reformat Code PARA QUE EL CÓDIGO QUEDE MÁS LIMPITO Y LOS OPERADORES
        // SE SEPAREN DE LOS OPERANDOS.
        if (cantidad <= 0)
            Toast.makeText(getApplicationContext(),"0",Toast.LENGTH_SHORT).show();
        else {
            if (idOrigen == dollarOrigin.getId()) {
                if (idCambio == euroChange.getId())
                    //Dolar a euro
                    cantidad_pasada = dolar_euro*cantidad;
                else
                    //Dolar a yen
                    cantidad_pasada = dolar_yen*cantidad;
            } else if (idOrigen == euroOrigin.getId()) {
                if (idCambio == dollarChange.getId())
                    //Euro a dolar
                    cantidad_pasada = cantidad/dolar_euro;
                else
                    //Euro a yen
                    cantidad_pasada = cantidad*euro_yen;
            } else if (idOrigen == yenOrigin.getId()) {
                if (idCambio == dollarChange.getId())
                    //Yen a dolar
                    cantidad_pasada = cantidad / dolar_yen;
                else
                    //Yen a euro
                    cantidad_pasada = cantidad / euro_yen;
            }
            // LAS MONEDAS SE MUESTRAN NORMALMENTE CON DOS DECIMALES. USA UN RECURSO DE CADENA
            // PARAMETRIZADO CON "%1$.2f %s = %2$.2f %s"
            Toast.makeText(getApplicationContext(),String.valueOf(cantidad_pasada),Toast.LENGTH_SHORT).show();
        }
    }

    protected int disableBtnOrigen(){
        int btnPressed = groupBtnOrigin.getCheckedRadioButtonId();

        if(btnPressed == dollarOrigin.getId()){
            dollarChange.setEnabled(false);
            euroChange.setEnabled(true);
            yenChange.setEnabled(true);
            imagenMonedaOrigen.setImageResource(R.drawable.ic_dollar);
        }
        else if(btnPressed == euroOrigin.getId()){
            dollarChange.setEnabled(true);
            euroChange.setEnabled(false);
            yenChange.setEnabled(true);
            imagenMonedaOrigen.setImageResource(R.drawable.ic_euro);
        }
        else if(btnPressed == yenOrigin.getId()){
            dollarChange.setEnabled(true);
            euroChange.setEnabled(true);
            yenChange.setEnabled(false);
            imagenMonedaOrigen.setImageResource(R.drawable.ic_yen);
        }
        return btnPressed;
    }

    protected int disableBtnChange(){
        int btnPressed = groupBtnChange.getCheckedRadioButtonId();

        if(btnPressed == dollarChange.getId()){
            dollarOrigin.setEnabled(false);
            euroOrigin.setEnabled(true);
            yenOrigin.setEnabled(true);
            imagenMonedaCambio.setImageResource(R.drawable.ic_dollar);
        }
        else if(btnPressed == euroChange.getId()){
            dollarOrigin.setEnabled(true);
            euroOrigin.setEnabled(false);
            yenOrigin.setEnabled(true);
            imagenMonedaCambio.setImageResource(R.drawable.ic_euro);
        }
        else if(btnPressed == yenChange.getId()) {
            dollarOrigin.setEnabled(true);
            euroOrigin.setEnabled(true);
            yenOrigin.setEnabled(false);
            imagenMonedaCambio.setImageResource(R.drawable.ic_yen);
        }

        return btnPressed;
    }

}
