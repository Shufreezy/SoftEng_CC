package com.example.admin.cyclecalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import java.util.ArrayList;

public class GlossaryFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    ExpandableListAdapter listAdapter;
    ExpandableListView lv;
    SearchView search;
    ArrayList<GlossaryItem> glossaryitems;

    public GlossaryFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_glossary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search = (SearchView) view.findViewById(R.id.search);
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        listAdapter = new ExpandableListAdapter(getContext(), glossaryitems);
        lv.setAdapter(listAdapter);

        // closes previously opened listitem
        lv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    lv.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
    }

    // SEARCH
    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        listAdapter.filterData(query);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        return false;
    }

    //Initialize Data for Glossary
    private void loadData() {

        glossaryitems = new ArrayList<GlossaryItem>();
        glossaryitems.add(new GlossaryItem("Adipose Tissue", "The majority of this connective tissue is made up of fat cells."
                + "It is located under the skin but can also be found between muscles,"
                + "intestines, around the heart and elsewhere.The stored fat in adipose"
                + "tissue comes primarily from dietary fats.It acts as a reserve or"
                + "storehouse of energy to protect the body from starvation and aids"
                + "the body in extreme exertion.The fat also pads the organs to protect"
                + "them from trauma and it contributes to regulating and conserving body heat."));
        glossaryitems.add(new GlossaryItem("Amenorrhea", "Is the absence of a menstrual period in a woman of reproductive age."));
        glossaryitems.add(new GlossaryItem("Anovulation", "A cycle in which ovulation does not occur."));
        glossaryitems.add(new GlossaryItem("Antral Follicles", "Ovarian follicles that measure between 2-10 mm at the start of the "
                + "menstrual cycle. These follicles represent the pool of oocytes that"
                + "are available for recruitment, growth, and ovulation in a given month."));
        glossaryitems.add(new GlossaryItem("Areola", "The pinkish brown skin that circles the nipple. It frequently darkens during pregnancy."));
        glossaryitems.add(new GlossaryItem("Basic Infertile Pattern", "An extended pattern of sticky or dry cervical fluid that women may "
                + "experience during an ovulatory cycle rather than the normal pattern "
                + "of progressively more watery and abundant cervical fluid."));
        glossaryitems.add(new GlossaryItem("Bladder", "A muscular sac in the pelvis, just above and behind the pubic bone. "
                + "When empty, the bladder is about the size and shape of a pear."
                + "The bladder stores urine, allowing urination to be infrequent and voluntary."
                + "The bladder is lined by layers of muscle tissue that stretch to accommodate urine."
                + "The normal capacity of the bladder is 400 to 600 mL. During urination,"
                + "the bladder muscles contract, and two sphincters (valves) open to allow urine"
                + "to flow out."));
        glossaryitems.add(new GlossaryItem("Breakthrough Bleeding", "Vaginal bleeding due to excessive estrogen production (and also usually due "
                + "to a lack of progesterone). Although breakthrough bleeding may seem like a"
                + " menstrual period, it is not a true menstrual period if ovulation has not"
                + "occurred prior to the bleeding."));
        glossaryitems.add(new GlossaryItem("Cervical Fluid", "The fluid produced by the cervix in which sperm can travel. The quantity "
                + "and type of cervical fluid present are directly related to the body’s "
                + "production of estrogen and progesterone. Cervical fluid is one of the "
                + "3 primary fertility signs along with basal body temperature and "
                + "cervical position."));
        glossaryitems.add(new GlossaryItem("Cervix", "The lower part of the uterus (womb) that connects to the vagina which "
                + "leads to the outside of the body. During menstruation, blood flows "
                + "from the uterus through the cervix and into the vagina. It is part "
                + "of the female reproductive system."));
        glossaryitems.add(new GlossaryItem("Corpus Luteum", "What is left of the follicle that released the egg at ovulation. "
                + "The corpus luteum produces progesterone to support the endometrium. "
                + "If conception occurs, the corpus luteum will continue to produce "
                + "progesterone throughout early pregnancy. Otherwise, the corpus luteum "
                + "will stop producing progesterone and resorbs 12-16 days later and a "
                + "new cycle begins."));
        glossaryitems.add(new GlossaryItem("Cystitis", "A medical term for the inflammation of the bladder. "
                + "This inflammation is usually caused by a bacterial infection and is "
                + "referred to as a urinary tract infection (UTI). It can become a serious "
                + "health problem if the infection spreads to the kidneys (pyelonephritis)."));
        glossaryitems.add(new GlossaryItem("Dysmenorrhea", "Refers to pain related to menstrual flow or cramping that is severe "
                + "enough to interfere with daily activities."));
        glossaryitems.add(new GlossaryItem("Endometrium", "The lining of the uterus that is built up by estrogen and supported "
                + "by progesterone during a cycle. If conception occurs, the fertilized "
                + "egg (embryo) will implant in the endometrium. If conception does not "
                + "occur, the endometrium is shed resulting in menstruation."));
        glossaryitems.add(new GlossaryItem("Estrogen", "Estrogen, one of the major hormones of pregnancy, causes the "
                + "endometrium (lining of the uterus) to thicken. Blood levels of "
                + "estrogen control the signal to the pituitary gland’s production "
                + "and release of FSH and LH. It is often used to monitor the ovarian "
                + "response to fertility medication."));
        glossaryitems.add(new GlossaryItem("Follicle", "The fluid filled sac in the ovary that contains the oocyte and is lined "
                + "by granulosa cells. During a menstrual cycle, follicles grow in response "
                + "to hormones released from the pituitary gland in the brain."));
        glossaryitems.add(new GlossaryItem("Luteal Phase", "Also called the secretory phase. The portion of the menstrual cycle "
                + "beginning immediately at ovulation until menstruation occurs."));
        glossaryitems.add(new GlossaryItem("Luteinizing Hormone", "A hormone produced by the pituitary gland (located in the base of the brain). "
                + "LH levels vary throughout the menstrual cycle. It increases rapidly "
                + "immediately prior to ovulation. This causes the dominant follicle to "
                + "release the egg from the ovary (ovulation). Metabolites of LH are can "
                + "be detected in the urine and are the basis for determining ovulation "
                + "when using a urinary LH prediction kit."));
        glossaryitems.add(new GlossaryItem("Menarche", "The medical term for a girl’s first period"));
        glossaryitems.add(new GlossaryItem("Menorrhagia", "Abnormally heavy or prolonged menstrual periods which occur at regular intervals."));
        glossaryitems.add(new GlossaryItem("Ovarian Cyst", "A fluid filled sac that forms on, or within, one or both ovaries. The vast majority of ovarian cysts are benign (non-cancerous). Most ovarian cysts resolved spontaneously without medical or surgical treatment."));

        glossaryitems.add(new GlossaryItem("Ovulation", "The release of a mature egg from the ovary. Approximately 90% of reproductive age women will ovulate on a monthly basis."));

        glossaryitems.add(new GlossaryItem("Pelvic Cavity", "The space inside the lower abdomen which contains the reproductive organs, "
                + "including the uterus, fallopian tubes, and ovaries. This space also contains "
                + "the urinary bladder, the pelvic colon, and the rectum."));

        glossaryitems.add(new GlossaryItem("Progesterone", "Progesterone is a hormone produced by the ovaries during ovulation "
                + "(the release of a mature egg from an ovary)and prepares the uterine"
                + "lining(endometrium) for implantation of an embryo.It may by prescribed "
                + "orally, as an intramuscular injection, vaginal gel, vaginal suppositories, "
                + "or transdermal cream."));

        glossaryitems.add(new GlossaryItem("Prolactin", "A hormone produced and released by the pituitary gland into the bloodstream which stimulates breast milk production."));

        glossaryitems.add(new GlossaryItem("Spinnbarkeit", "Refers to cervical fluid becoming abundant, clear and stretchable, often described as resembling egg - white.This occurs during the ovulatory window."));

        glossaryitems.add(new GlossaryItem("Uterine Fibroids", "These benign growths develop from smooth muscular tissue of the uterus "
                + "when a single cell divides multiple times.Eventually resulting in a "
                + "firm mass of tissue.Estrogen stimulates the growth of fibroids so it "
                + "is not uncommon for fibroids to shrink after "
                + "menopause.However, hormone "
                + "therapy can cause symptoms to persist.These tumors are fairly common "
                + "and occur in roughly 70 - 80 % of women by the time they reach perimenopause. "
                + "They rarely cause symptoms or discomfort and a woman may never become aware "
                + "of its presence. "));

        glossaryitems.add(new GlossaryItem("Uterus", "(also known as the womb) – A women’s reproductive organ where the fetus grows and menstrual bleeding originates."));

        glossaryitems.add(new GlossaryItem("Vagina", "A pliable muscular canal, which connects the external genitalia to the cervix  (the lower part of the uterus)."));

        glossaryitems.add(new GlossaryItem("Vulva", "Includes the female external genital organs such as the labia majora and minora, clitoris, and vaginal opening."));
    }
}
