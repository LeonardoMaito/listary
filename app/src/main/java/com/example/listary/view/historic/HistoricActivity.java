package com.example.listary.view.historic;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;

import com.example.listary.R;
import com.example.listary.adapters.HistoricAdapter;
import com.example.listary.controllers.HistoricController;
import com.example.listary.model.ShoppingListDocument;
import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.example.listary.view.loginForm.LoginActivity;
import com.example.listary.view.menu.MenuActivity;
import com.example.listary.view.newList.NewListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class HistoricActivity extends AppCompatActivity {

    private RecyclerView rvHistoric;
    private HistoricAdapter historicAdapter;
    public static Activity self_intent;
    private HistoricController historicController = new HistoricController();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);
        self_intent = this;

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,

                DividerItemDecoration.VERTICAL);

        rvHistoric = findViewById(R.id.rvHistoric);
        rvHistoric.setLayoutManager(new GridLayoutManager(this,2));
        rvHistoric.setHasFixedSize(true);
        rvHistoric.addItemDecoration(dividerItemDecoration);

        Query query = historicController.getDocRef().orderBy("shoppingList",
                Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ShoppingListDocument> options =
                new FirestoreRecyclerOptions.Builder<ShoppingListDocument>()
                        .setQuery(query, ShoppingListDocument.class)
                        .build();

        historicAdapter = new HistoricAdapter(options);
        rvHistoric.setAdapter(historicAdapter);
        historicAdapter.notifyDataSetChanged();

        this.setTitle(getResources().getString(R.string.historic));
    }

    @Override
    protected void onStart() {
        super.onStart();
        historicAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        historicAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header_historic, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.menuListary:
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.novaLista:
                intent = new Intent(this, NewListActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.consultarProduto:
                intent = new Intent(this, SearchProductActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.despensa:
                intent = new Intent(this, PantryActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            default:
                return true;
        }
    }

}