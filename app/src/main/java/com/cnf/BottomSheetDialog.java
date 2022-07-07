package com.cnf;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable
      ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View v = inflater.inflate(R.layout.bottom_nav_layout,
        container, false);

    return v;
  }
}
