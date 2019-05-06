package foodget.ihm.foodget.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import foodget.ihm.foodget.R;
import foodget.ihm.foodget.activities.LoginActivity;
import foodget.ihm.foodget.activities.MyAccountActivity;
import foodget.ihm.foodget.activities.NewMailActivity;
import foodget.ihm.foodget.activities.NewNameActivity;
import foodget.ihm.foodget.activities.NewPasswordActivity;
import foodget.ihm.foodget.models.User;

public class TabMyAccount extends Fragment {

    Button UpdateMail;
    Button UpdateName;
    Button UpdatePassWord;
    Button Logout;
    User currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate fragment_tab1
        final View view = inflater.inflate(R.layout.activity_myaccount, container, false);



        UpdateMail = (Button) view.findViewById(R.id.ModifierMailButton);
        UpdateName = (Button) view.findViewById(R.id.ModifierPrenomButton);
        UpdatePassWord = (Button) view.findViewById(R.id.ModifierMDPButton);
        Logout = (Button) view.findViewById(R.id.LogoutButton);
        currentUser = this.getArguments().getParcelable("user");



        UpdateMail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent NewMailIntent= new Intent(getContext(), NewMailActivity.class);
                NewMailIntent.putExtra("USER", currentUser);
                startActivity(NewMailIntent);
            }
        });
        UpdateName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent NewNameIntent= new Intent(getContext(), NewNameActivity.class);
                NewNameIntent.putExtra("USER", currentUser);
                startActivity(NewNameIntent);
            }
        });
        UpdatePassWord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent NewPasswordIntent= new Intent(getContext(), NewPasswordActivity.class);
                NewPasswordIntent.putExtra("USER", currentUser);
                startActivity(NewPasswordIntent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent LogOutIntent= new Intent(getContext(), LoginActivity.class);
                startActivity(LogOutIntent);
            }
        });
        return view;
    }
}
