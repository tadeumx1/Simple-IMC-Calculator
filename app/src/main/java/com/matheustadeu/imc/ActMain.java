package com.matheustadeu.imc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Advanceable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DecimalFormat;

public class ActMain extends AppCompatActivity {

    private EditText edtPeso;
    private EditText edtAltura;
    private TextView txvResultado;
    private TextView txvResultado1;

    AdView mAdView;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        edtPeso = (EditText) findViewById(R.id.edtPeso);
        edtAltura = (EditText) findViewById(R.id.edtAltura);
        txvResultado = (TextView) findViewById(R.id.txvResultado);
        txvResultado1 = (TextView) findViewById(R.id.txvResultado1);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_tabela);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_compartilhar);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_sobre);

        MobileAds.initialize(this,"ca-app-pub-9500978193198107~1062385601");

        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked

                ImageView image = new ImageView(ActMain.this);
                image.setImageResource(R.drawable.a20180110_161646);

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(ActMain.this).
                                setMessage("Tabela do IMC").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(image);
                builder.create().show();

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/pain");
                String shareBody = "Quer ver seu IMC ? Baixe agora o Cálculo IMC - Peso Ideal ! " +
                        "\n https://play.google.com/store/apps/details?id=com.matheustadeu.imc ";
                String shareSub= "Cálculo IMC - Peso Ideal";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                startActivity(Intent.createChooser(shareIntent,"Compartilhar usando"));
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

                AlertDialog alertDialog = new AlertDialog.Builder(ActMain.this).create();
                alertDialog.setTitle("Sobre o aplicativo");
                alertDialog.setMessage("Desenvolvido por Matheus Tadeu" +
                        "\n \nContato : matheustadeu.com" +
                        "\n \n2018" +
                        "\n \nNão se equeça de compartilhar o aplicativo com seus amigos ! " +
                        "\n \nE também avaliar o aplicativo ! ");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });

    }

    public void onCalcular(View view) {

        if (edtAltura.getEditableText().toString().trim().equals("")) {

            Toast.makeText(ActMain.this, "Digite sua altura ! ", Toast.LENGTH_SHORT).show();
            edtAltura.requestFocus();

        } else if (edtPeso.getEditableText().toString().trim().equals("")) {

            Toast.makeText(ActMain.this, "Digite seu peso ! ", Toast.LENGTH_SHORT).show();
            edtPeso.requestFocus();

        }

        else {

            // - Coletar as infromações

            String coletaAltura = edtAltura.getEditableText().toString();
            String coletaPeso = edtPeso.getEditableText().toString();

            // - Converter String

       /* Usar Float */
            double altura = Double.parseDouble(coletaAltura);
            double peso = Double.parseDouble(coletaPeso);

            // - Calcular

            double resultado = (peso / (altura * altura));

            DecimalFormat df2 = new DecimalFormat(".##");

            String resultado1 = df2.format(resultado);

            String mensagem = new String();

            if (resultado < 17) {
                mensagem = ("Muito abaixo do peso");
            } else if (resultado >= 17 && resultado <= 18.49) {
                mensagem = ("Abaixo do peso");
            } else if (resultado >= 18.50 && resultado <= 24.99) {
                mensagem = ("Peso normal");
            } else if (resultado >= 25 && resultado <= 29.99) {
                mensagem = ("Acima do peso");
            } else if (resultado >= 30 && resultado <= 34.99) {
                mensagem = ("Obesidade I");
            } else if (resultado >= 35 && resultado <= 39.99) {
                mensagem = ("Obesidade II (severa)");
            } else if (resultado >= 40) {
                mensagem = ("Obesidade III (mórbida)");
            } else {
                mensagem = ("Calculo errado!");
            }

            txvResultado.setText(resultado1);
            txvResultado1.setText(mensagem);

            edtAltura.setText("");
            edtPeso.setText("");

            if(txvResultado.equals(",0") || txvResultado.equals(".0")) {

                Toast.makeText(this,"Erro ! Caso o erro continue resintale o aplicativo ! ", Toast.LENGTH_LONG).show();

            }

        }

    }

}
