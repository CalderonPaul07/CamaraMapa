// Generated by view binder compiler. Do not edit!
package ec.edu.tecnologicoloja.mapslocation.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import ec.edu.tecnologicoloja.mapslocation.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final Button btnCamara;

  @NonNull
  public final Button btnRegresar;

  @NonNull
  public final ImageView imageFoto;

  @NonNull
  public final Toolbar toolbar;

  private ActivityMainBinding(@NonNull LinearLayoutCompat rootView, @NonNull Button btnCamara,
      @NonNull Button btnRegresar, @NonNull ImageView imageFoto, @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.btnCamara = btnCamara;
    this.btnRegresar = btnRegresar;
    this.imageFoto = imageFoto;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnCamara;
      Button btnCamara = ViewBindings.findChildViewById(rootView, id);
      if (btnCamara == null) {
        break missingId;
      }

      id = R.id.btnRegresar;
      Button btnRegresar = ViewBindings.findChildViewById(rootView, id);
      if (btnRegresar == null) {
        break missingId;
      }

      id = R.id.imageFoto;
      ImageView imageFoto = ViewBindings.findChildViewById(rootView, id);
      if (imageFoto == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new ActivityMainBinding((LinearLayoutCompat) rootView, btnCamara, btnRegresar,
          imageFoto, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}