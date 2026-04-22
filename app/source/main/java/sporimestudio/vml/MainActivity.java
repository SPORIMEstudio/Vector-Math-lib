
package sporimestudio.vml;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import sporimestudio.vml.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Vector2f vector1,vector2;
    private EditText evx1,evy1,evx2,evy2;
    private Button mult,subt,add,sqr,mag;
    private TextView tv1;
    private String output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        evx1 = findViewById(R.id.Vx1);
        evx2 = findViewById(R.id.vx2);
        evy1 = findViewById(R.id.vy1);
        evy2 = findViewById(R.id.vy2);
        mult = findViewById(R.id.mul);
        subt =findViewById(R.id.sub);
        add = findViewById(R.id.add);
        mag  = findViewById(R.id.mag);
        tv1 = findViewById(R.id.out);
        
       //try {
            float vx1 = evx1.getText().toString().isEmpty() ? 0 : Float.parseFloat(evx1.getText().toString());
            float vy1 = evy1.getText().toString().isEmpty() ? 0 : Float.parseFloat(evy1.getText().toString());
            float vx2 = evx2.getText().toString().isEmpty() ? 0 : Float.parseFloat(evx2.getText().toString());
            float vy2 = evy2.getText().toString().isEmpty() ? 0 : Float.parseFloat(evy2.getText().toString());
       /*     // Use these values
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }*/
        
        vector1 = new Vector2f(vx1,vy1);
        vector2 = new Vector2f(vx2,vy2);
        mult.setOnClickListener(v->{
            output = vector1.mul(vector2).toString();
                tv1.setText(output);
        });
        subt.setOnClickListener(v->{
            output= vector1.subtract(vector2).toString();
        });
        add.setOnClickListener(v->{
            output = vector1.sum(vector2).toString();
        });
     /*   mag.setOnClickListener(v->{
            output = vector1.magnitude().toString();
        });*/
       /* Vector2f vec = new Vector2f(2,2);
        String output = vec.mul(new Vector2f(2,2)).toString();*/
        
        
        tv1.setText(output);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
