package com.adiant.android.ads.example;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.adiant.android.ads.NativeAdFactory;
import com.adiant.android.ads.NativeAdArbiter;
import com.adiant.android.ads.NativeAdAdapter;
import com.adiant.android.ads.NativeAdInflater;
import com.adiant.android.ads.util.ViewBinder;

public class ItemListActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }

        ItemListFragment fragment = new ItemListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    public static class ItemListFragment extends ListFragment {
        private NativeAdAdapter adapterWithAds;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle  savedInstanceState) {
            // We'll define a custom screen layout here (the one shown above), but
            // typically, you could just use the standard ListActivity layout.
            return inflater.inflate(R.layout.fragment_list, container, false);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // create ad factory
            NativeAdFactory adFactory = new NativeAdFactory(getActivity(), "13325-2514722925", new NativeAdArbiter());

            // estimate total items shown
            adFactory.init(10);

            // Now create a new list adapter for this example
            // Use large blocks of text so not all items load at once. This gives us time to preload ads.
            ListAdapter adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    new String[]{
                            "0 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut tincidunt, mauris eget commodo mattis, libero neque aliquam sem, et vulputate lectus sem id neque. Quisque urna lorem, blandit vel sapien eget, tristique hendrerit ex. Quisque quis interdum justo. Nam a ornare nisi, id posuere erat. Nulla facilisi. Nam sed tellus a dui placerat egestas vitae sit amet ipsum. Duis luctus quam sit amet pulvinar pellentesque. Nulla finibus, diam ac suscipit dictum, lorem risus molestie ligula, commodo porttitor lorem nulla pellentesque risus. Aliquam sapien est, gravida nec metus vitae, volutpat porttitor ante. Sed ullamcorper, lacus eget rutrum porta, mauris tellus dapibus nibh, vitae pretium odio lacus a nisl. Donec nec eros a ligula egestas pretium ut eu justo. Sed mollis tempus nibh id consectetur. Suspendisse potenti.",
                            "1 Curabitur interdum ultricies metus, vel porttitor lacus volutpat nec. Nam id cursus odio. Morbi suscipit erat sem, non sagittis arcu posuere eget. Quisque semper cursus justo et facilisis. Ut sit amet augue non ex consequat efficitur non et tellus. Suspendisse quis sem faucibus nisl lobortis interdum non at enim. Vestibulum eu sollicitudin turpis, et aliquet erat. Curabitur aliquam, tortor in congue commodo, ex lectus fermentum ligula, vulputate vehicula elit ligula nec odio. Fusce in sollicitudin mi. Cras nec neque elementum erat tincidunt rhoncus. Aliquam sit amet felis vel magna consectetur aliquet. Phasellus in risus vel urna scelerisque elementum. Duis eget eleifend lorem, sed scelerisque enim.",
                            "2 Pellentesque dignissim urna in semper pharetra. Nam purus neque, auctor eget pellentesque at, vehicula vitae magna. In semper urna felis, luctus dapibus quam condimentum nec. Cras metus mauris, eleifend et purus vitae, blandit feugiat libero. Nulla vitae pulvinar orci, ac accumsan magna. Nam in bibendum tellus. Maecenas laoreet tincidunt ligula dignissim tincidunt. Donec dolor tellus, gravida ac orci eu, elementum pellentesque nibh. Proin ac neque pulvinar, tristique neque quis, ultricies dolor.",
                            "3 Pellentesque ut eros malesuada, rutrum tortor ac, ornare magna. Etiam id finibus ligula, eu tempor ligula. Suspendisse mauris odio, sollicitudin nec rutrum vel, tincidunt eget neque. Integer condimentum et quam eu tristique. Vestibulum non metus ornare, pulvinar eros in, accumsan magna. Quisque sagittis ligula non sem volutpat, dictum pellentesque turpis cursus. Sed eget consequat diam. Vestibulum ac nunc sem. Curabitur pulvinar, orci pretium luctus euismod, sem nibh malesuada dolor, sed hendrerit nisi tortor at dui. Pellentesque pulvinar sapien mi, nec eleifend felis ullamcorper nec. Morbi pulvinar purus quis lacus posuere, non aliquet lacus maximus. Mauris at enim sagittis, dapibus dolor finibus, imperdiet neque. Integer sagittis diam sit amet est aliquet commodo. Mauris bibendum nec metus quis scelerisque.",
                            "4 Suspendisse sed elit sit amet justo laoreet laoreet. Aliquam interdum velit et mauris rutrum, vitae faucibus eros imperdiet. Maecenas ac bibendum orci. Donec pretium, nisi vel feugiat suscipit, enim arcu faucibus eros, eu luctus nunc velit ac orci. Cras urna erat, hendrerit non consectetur sed, auctor laoreet elit. Vestibulum quis gravida purus. Maecenas mollis arcu vitae ullamcorper fermentum. Nunc bibendum vestibulum metus, sit amet facilisis felis eleifend eu. In fermentum tellus a faucibus consequat. Nunc eu iaculis urna. Nam at arcu pretium velit sollicitudin luctus. Ut laoreet lacinia rutrum.",
                            "5 Vestibulum dictum nisl leo, at semper felis dignissim at. Suspendisse lacinia lorem vitae efficitur vulputate. Praesent a mauris purus. Vivamus lorem nisl, lacinia a metus eu, convallis laoreet nisl. Donec aliquet ultrices elit a porttitor. Duis commodo ultricies vulputate. Mauris vulputate porta aliquet. Etiam hendrerit sodales aliquet. Suspendisse quis massa vel tortor pharetra placerat. Nulla sed lorem pharetra, accumsan metus id, gravida nisl. Duis euismod ac velit quis varius.",
                            "6 Pellentesque convallis ligula eu dolor volutpat commodo. Nulla sed tincidunt lorem. Quisque ullamcorper, odio a pharetra interdum, eros justo eleifend ligula, feugiat condimentum ligula nibh auctor mauris. Nunc et hendrerit enim, eget sagittis tellus. Quisque massa erat, congue laoreet volutpat ut, iaculis quis velit. In hac habitasse platea dictumst. Aenean vitae fringilla est, eleifend tempor lorem. Ut faucibus, ligula id tempor scelerisque, urna augue laoreet urna, non mattis massa leo in velit. Aenean nunc ex, tincidunt sit amet nibh semper, dapibus consequat metus. Morbi sit amet nibh magna. Maecenas vehicula orci quis vehicula tempus. Etiam tincidunt vestibulum quam dapibus consectetur. Phasellus non sollicitudin dolor, ut iaculis dolor. Nulla convallis nec arcu eget sodales. Duis nec odio at lectus mollis rutrum. Quisque nec tortor mauris."
                    });

            // Bind to our new adapter.
            ViewBinder binder = new ViewBinder.Builder()
                    .setImageId(R.id.imageView)
                    .setTitleId(R.id.textViewTitle)
                    .setDescriptionId(R.id.textViewDescription)
                    .setDisplayNameId(R.id.textViewDisplayName)
                    .create();

            // create ad inflater with the resource ID for the native ad layout
            NativeAdInflater adInflater = new NativeAdInflater(getActivity(), binder,
                    R.layout.list_item_native_ad);

            // wrap adapter inside adapter that inserts native ads
            adapterWithAds = new NativeAdAdapter(adapter, adInflater, adFactory);

            // use the adapter
            setListAdapter(adapterWithAds);
        }

        @Override
        public void onDestroy() {
            if (adapterWithAds != null) {
                adapterWithAds.destroy();
            }
            super.onDestroy();
        }
    }
}
