package eu.soilErasmus.soil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;
import androidx.media3.exoplayer.ExoPlayer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ARCamera extends AppCompatActivity implements
        FragmentOnAttachListener,
        BaseArFragment.OnTapArPlaneListener,
        BaseArFragment.OnSessionConfigurationListener,
        ArFragment.OnViewCreatedListener {

    ArFragment arFragment;
    Renderable model;
    ViewRenderable viewRenderable;
    private ArrayList<TransformableNode> modelList;
    private ArrayList<AnchorNode> anchorList;
    private ArrayList<Node> nodeList;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ar_camera);
        getSupportFragmentManager().addFragmentOnAttachListener(this);

        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, ArFragment.class, new Bundle())
                        .commit();
            }
        }
        deleteButton = findViewById(R.id.deleteButton);
    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            arFragment = (ArFragment) fragment;
            arFragment.setOnSessionConfigurationListener(this);
            arFragment.setOnViewCreatedListener(this);
            arFragment.setOnTapArPlaneListener(this);
        }
    }

    @Override
    public void onSessionConfiguration(Session session, Config config) {
        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC);
        }
    }

    @Override
    public void onViewCreated(ArSceneView arSceneView) {
        arFragment.setOnViewCreatedListener( arSceneView1->
                arSceneView1.setFrameRateFactor(SceneView.FrameRate.FULL)
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        deleteButton.setOnClickListener(view -> deleteModels());
        modelList = new ArrayList<TransformableNode>();
        anchorList = new ArrayList<AnchorNode>();
        nodeList = new ArrayList<Node>();

        Bundle plantPageInteger = getIntent().getExtras();
        int modelResource = plantPageInteger.getInt("modelForAR");
        loadModels(modelResource);
    }

    public void loadModels(int modelResource) {
        WeakReference<ARCamera> weakActivity = new WeakReference<>(this);

        ModelRenderable.builder()
                .setSource(this, modelResource)
                .setIsFilamentGltf(true)
                .build()
                .thenAccept(model -> {
                    ARCamera activity = weakActivity.get();
                    if (activity != null) {
                        activity.model = model;
                    }
                });

        ViewRenderable.builder()
                .setView(this, R.layout.view_3dmodel_title)
                .build()
                .thenAccept(viewRenderable -> {
                    ARCamera activity = weakActivity.get();
                    if (activity != null) {
                        activity.viewRenderable = viewRenderable;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });
    }

    public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        if (model == null || viewRenderable == null) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
            return;
        }


        // Create the Anchor.
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        anchorList.add(anchorNode);

        // Create the transformable model and add it to the anchor.
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(this.model).animate(true).start();

        modelList.add(transformableNode);

        Node titleNode = new Node();
        titleNode.setParent(transformableNode);
        titleNode.setEnabled(false);
        titleNode.setLocalPosition(new Vector3(0.0f, 1.0f, 0.0f));
        titleNode.setRenderable(viewRenderable);
        titleNode.setEnabled(true);

        nodeList.add(titleNode);

    }

    public void deleteModels(){

        for (TransformableNode transformableNode : modelList){
            transformableNode.getRenderableInstance().destroy();

        }

        for (Node titleNode : nodeList){
            titleNode.setParent(null);

        }

        for (AnchorNode anchor : anchorList){
            anchor.setAnchor(null);
        }

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        deleteModels();
    }
}

/*
        Button deleteNodeButton = findViewById(R.id.deleteAll);
        deleteNodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ARCamera.this);
                builder.setTitle("Trash all?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        for ( i = 0; i < modelList.size(); i++) {
                            Toast.makeText(getApplicationContext(),"yo",Toast.LENGTH_SHORT).show();
                            //Διαγραφει μονο τα μοντελα και οχι τα γραφικα MemoryOutOfBounds
                            // modelList.get(i).setRenderable(null);
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog mDialog = builder.create();
                mDialog.show();

            }
        });
 */