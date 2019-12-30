package v.fsa.com.startup.fsa;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import v.fsa.com.share.AndroidMultiPartEntity.MenuList;
import v.fsa.com.share.AndroidMultiPartEntity.MenuListAdapter;
import v.fsa.com.startup.fsa.v.fsa.com.config.Config.Config;

public class UserHome extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MenuListAdapter adapter;
    private List<MenuList> albumList;
    HashMap<Integer,Class> classList;

    public static Config link=new Config();
    private String URL_USER_REGISTRATION= Config.getServerLink()+"user_login.php";
    private ProgressDialog progressDialog;

    //public HashMap<Integer,Class> classList=new HashMap<>();

    TextView txtTotalReq;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip);


        txtTotalReq=(TextView)findViewById(R.id.total_req);


        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        albumList = new ArrayList<>();
        prepareAlbums();


        adapter = new MenuListAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);


        //System.out.println("Dp Box ="+dpToPx(10));

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



        //getTotalRequisition();


        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);

        // swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark,R.color.colorPrimary);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               // getTotalRequisition();

                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void prepareAlbums(){


        int[] covers = new int[]{
                R.drawable.add,
                R.drawable.report,
                R.drawable.profile,
                R.drawable.call_999,
                R.drawable.logout,
        };

        MenuList a = new MenuList("Nearest FireStation", covers[0],"Nearest FireStation");
        albumList.add(a);



        a = new MenuList("Emergency Contact", covers[1],"Emergency Contact");
        albumList.add(a);


        a = new MenuList("Login", covers[2],"For Live Sharing");
        albumList.add(a);

        a = new MenuList("Call 999", covers[3],"Help Line");
        albumList.add(a);



    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }



            }
        });
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position

            int column = position % spanCount; // item column

            if (includeEdge) {


            /*    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
                */

                outRect.left=30;
                outRect.right=30;
                outRect.top=30;
                outRect.bottom=30;


            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }

                outRect.bottom=18;
            }



        }
    }
}
