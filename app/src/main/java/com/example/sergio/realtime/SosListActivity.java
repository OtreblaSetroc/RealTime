package com.example.sergio.realtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.realtime.dummy.DummyContent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.ButterKnife;

/**
 * An activity representing a list of Soses. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link SosDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class SosListActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private EditText edtName;
    private  EditText edtFrente;
    private Button btnsave;
    private static  final String PATH_SOS="SOS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_list);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.sos_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
         init();
        View recyclerView = findViewById(R.id.sos_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


        btnsave.setOnClickListener(this);
    }

    public  void  init(){
        edtName=(EditText) findViewById(R.id.edtName);
        edtFrente=(EditText) findViewById(R.id.edtFrente);
        btnsave=(Button) findViewById(R.id.btnSave);
    }
    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference reference= database.getReference(PATH_SOS);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DummyContent.Sos sos = dataSnapshot.getValue(DummyContent.Sos.class);
                sos.setId(dataSnapshot.getKey());
                if (!DummyContent.ITEMS.contains(sos)){

                    DummyContent.addItem(sos);
                }
                recyclerView.getAdapter().notifyDataSetChanged();



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DummyContent.Sos sos = dataSnapshot.getValue(DummyContent.Sos.class);
                sos.setId(dataSnapshot.getKey());
                if (DummyContent.ITEMS.contains(sos)){

                    DummyContent.updateItem(sos);
                }
                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DummyContent.Sos sos = dataSnapshot.getValue(DummyContent.Sos.class);
                sos.setId(dataSnapshot.getKey());
                if (DummyContent.ITEMS.contains(sos)){

                    DummyContent.deteleItem(sos);
                }
                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getApplicationContext(),"Moved",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        DummyContent.Sos sos = new DummyContent.Sos(edtName.getText().toString().trim(),
                edtFrente.getText().toString().trim());
        Toast.makeText(getApplicationContext(),"Save",Toast.LENGTH_LONG).show();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference= firebaseDatabase.getReference(PATH_SOS);
        reference.push().setValue(sos);
        edtFrente.setText("");
        edtName.setText("");

    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final SosListActivity mParentActivity;
        private final List<DummyContent.Sos> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.Sos item = (DummyContent.Sos) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();

                    arguments.putString(SosDetailFragment.ARG_ITEM_ID, item.getId());
                    SosDetailFragment fragment = new SosDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.sos_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, SosDetailActivity.class);
                    intent.putExtra(SosDetailFragment.ARG_ITEM_ID, item.getId());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(SosListActivity parent,
                                      List<DummyContent.Sos> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sos_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mIdView.setText("Frente "+mValues.get(position).getFrente());
            holder.mContentView.setText(mValues.get(position).getNombre());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference(PATH_SOS);
                    reference.child(mValues.get(position).getId()).removeValue();
                }
            });

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final  Button  btnDelete;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.nombre);
                btnDelete=view.findViewById(R.id.btnDelete);
            }
        }
    }
}
