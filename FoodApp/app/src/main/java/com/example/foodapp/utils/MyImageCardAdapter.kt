import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.database.Meal
import com.example.foodapp.utils.MyAdapter
import kotlinx.coroutines.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MyImageCardAdapter (private val mealList:ArrayList<Meal>) :
    RecyclerView.Adapter<MyImageCardAdapter.MyViewHolder>(){

    lateinit private var description:String
    lateinit private var line:String
    lateinit private var ingredients:ArrayList<String?>
    lateinit private var measures:ArrayList<String?>
    private val desTitles=arrayListOf("Meal","DrinkAlternate","Category","Area","Instructions","Tags","Youtube")



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyImageCardAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.search_item_image_card,
            parent,false)
        return MyImageCardAdapter.MyViewHolder(itemView)    }

    override fun onBindViewHolder(holder: MyImageCardAdapter.MyViewHolder, position: Int) {
        val currentItem=mealList[position]
        holder.cardTitle.text=currentItem.Meal
        holder.cardSubtitle.text=currentItem.Category

        setDescription(currentItem)
        holder.expandableDescription.text=description

        if (currentItem.MealThumb.equals("null")){
            holder.thumbnailImg.setImageResource(R.drawable.thumb)
        }
        else{
            try {
                GlobalScope.launch(Dispatchers.IO) {
                    val imageUrl = URL(currentItem.MealThumb)
                    val connection = imageUrl.openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    val inputStream = connection.inputStream
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    // Update the UI on the main thread
                    launch(Dispatchers.Main) {
                        holder.thumbnailImg.setImageBitmap(bitmap)
                    }
                }
            }catch (e:Exception){
                print(e.toString())
            }



        }


        val isVisible:Boolean=currentItem.visibility

        holder.expandableLayout.visibility=if(isVisible){
            View.VISIBLE
        }else{
            View.GONE
        }

        holder.cardIcon.setOnClickListener{
            currentItem.visibility=!currentItem.visibility
            notifyItemChanged(position)
        }
    }

    private fun setDescription(meal: Meal) {
        description=""
        line=""

        line = "\""+desTitles.get(0)+"\""+" : \""+meal.Meal+"\"\n"
        description=description+line
        line = "\""+desTitles.get(1)+"\""+" : \""+meal.DrinkAlternate+"\"\n"
        description=description+line
        line = "\""+desTitles.get(2)+"\""+" : \""+meal.Category+"\"\n"
        description=description+line
        line = "\""+desTitles.get(3)+"\""+" : \""+meal.Area+"\"\n"
        description=description+line
        line = "\""+desTitles.get(4)+"\""+" : \""+ (meal.Instructions?.substring(0,25) ?: meal.Instructions)+"...."+"\"\n"
        description=description+line
        line = "\""+desTitles.get(5)+"\""+" : \""+meal.Tags+"\"\n"
        description=description+line
        line = "\""+desTitles.get(6)+"\""+" : \""+meal.YouTube+"\"\n"
        description=description+line

        ingredients=meal.Ingredients
        measures=meal.Measure

        for (i in 0..ingredients.size-1){
            if (ingredients.get(i).isNullOrEmpty()){
                continue
            }
            else{
                line = "\""+"Ingredient"+(i+1)+"\""+" : \""+ingredients.get(i)+"\"\n"
                description=description+line
            }
        }
        for (i in 0..measures.size-1){
            if (measures.get(i).isNullOrBlank()){
                continue
            }
            else{
                line = "\""+"Measure"+(i+1)+"\""+" : \""+measures.get(i)+"\"\n"
                description=description+line
            }
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val thumbnailImg: ImageView=itemView.findViewById(R.id.imgTumb)
        val cardTitle:TextView=itemView.findViewById(R.id.cardTitle)
        val cardSubtitle:TextView=itemView.findViewById(R.id.cardSubtitle)
        val cardIcon:ImageButton=itemView.findViewById(R.id.cardIcon)
        val expandableLayout:LinearLayout=itemView.findViewById(R.id.expandableLayout)
        val expandableDescription:TextView=itemView.findViewById(R.id.expandableDescription)
    }
    
    
}

