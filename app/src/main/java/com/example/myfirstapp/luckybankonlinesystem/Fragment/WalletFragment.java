package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.luckybankonlinesystem.Model.AccountModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link WalletFragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String uID;
    private TextView nameTv, accnumTv;
    private String thisName, thisAccNum;
    AccountModel am = new AccountModel();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    public WalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //uID = user.getUid();
        uID = "D57bMMXqpwRAJC8zDfisd1YvKgm1";



        //thisName = FirebaseAuth.getInstance()..getDisplayName();
//        nameTv.setText(uID);

//
//        if (user != null) {
//            for (UserInfo profile : user.getProviderData()) {
//                // Id of the provider (ex: google.com)
//                String providerId = profile.getProviderId();
//
//                // UID specific to the provider
//                String uid = profile.getUid();
//
//                // Name, email address, and profile photo Url
//                String name = profile.getDisplayName();
//                String email = profile.getEmail();
//                Uri photoUrl = profile.getPhotoUrl();
//            }
//        }


        db.collection("users").document(uID).collection("accounts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //am = (AccountModel) queryDocumentSnapshots.toObjects(AccountModel.class);
                       // accnumTv.setText(am.getAccountNumber());
                        //thisAccNum = queryDocumentSnapshots.toObjects(am);
                    }
                });


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallet_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        nameTv = (TextView) getView().findViewById(R.id.tvUserName);
        nameTv.setText(uID);

        accnumTv = (TextView) getView().findViewById(R.id.tvAccnumber);
        accnumTv.setText(uID);
    }
}