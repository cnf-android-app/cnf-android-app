package com.cnf.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.InspectionActivity;
import com.cnf.R;
import com.cnf.adapter.InspectionLocationDescriptionAdapter;
import com.cnf.domain.OccLocationDescription;
import com.cnf.service.api.InspectionActivityService;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InspectionSelectLocationDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InspectionSelectLocationDescriptionFragment extends Fragment {

    private List<OccLocationDescription> locationDescriptionList;
    private InspectionActivityService inspectionActivityService;
    private RecyclerView inspection_location_description_rv;
    private  int CSTId;

    public InspectionSelectLocationDescriptionFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public InspectionSelectLocationDescriptionFragment(int CSTId) {
        this.CSTId = CSTId;
    }



    public static InspectionSelectLocationDescriptionFragment newInstance() {
        InspectionSelectLocationDescriptionFragment fragment = new InspectionSelectLocationDescriptionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inspection_select_location_description, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Thread t = new Thread() {
            @Override
            public void run() {
                inspectionActivityService = InspectionActivityService.getInstance(getActivity());
                try {
                    locationDescriptionList = inspectionActivityService.getOccLocationDescriptionList(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inspection_location_description_rv = getActivity().findViewById(R.id.rv_inspection_location_description);
        inspection_location_description_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        InspectionLocationDescriptionAdapter inspectionLocationDescriptionAdapter = new InspectionLocationDescriptionAdapter(this, CSTId, getActivity(),locationDescriptionList );
        inspection_location_description_rv.setAdapter(inspectionLocationDescriptionAdapter);

        Button backBtn = getActivity().findViewById(R.id.btn_location_description_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, new InspectionSelectSpaceTypeFragment()).commit();
            }
        });

        Button cancelBtn = getActivity().findViewById(R.id.btn_location_description_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InspectionActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

    }


}