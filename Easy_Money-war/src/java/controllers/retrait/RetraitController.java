package controllers.retrait;

import entities.AnneeMois;
import entities.Caisse;
import entities.Client;
import entities.Privilege;
import entities.Retrait;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class RetraitController extends AbstractRetraitController implements Serializable {

    @PostConstruct
    private void init() {
        /*  37 */ this.client = new Client();
        /*  38 */ this.retrait = new Retrait();
        /*  39 */ this.anneeMois = new AnneeMois();
    }

    public void updateMois() {
        try {
            /*  44 */ this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
            /*  45 */        } catch (Exception e) {
            /*  46 */ e.printStackTrace();
        }
    }

    public void prepareCreate() {
        /*  51 */ this.commission = Integer.valueOf(0);
        /*  52 */ this.retrait1 = Integer.valueOf(0);
        /*  53 */ this.client = new Client();
        /*  54 */ this.retrait = new Retrait();
        /*  55 */ this.retrait.setDate(SessionMBean.getDate());
        /*  56 */ this.anneeMois = SessionMBean.getMois();

        /*  58 */ this.mode = "Create";
        /*  59 */ this.showClient = Boolean.valueOf(false);

        try {
            /*  62 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  63 */ if (p != null) {
                /*  64 */ this.showRetraitCreateDialog = Boolean.valueOf(true);
            } else {
                /*  66 */ p = new Privilege();
                /*  67 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 13);
                /*  68 */ if (p != null) {
                    /*  69 */ this.showRetraitCreateDialog = Boolean.valueOf(true);
                } else {
                    /*  71 */ this.showRetraitCreateDialog = Boolean.valueOf(false);
                    /*  72 */ JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'enregistrer un retrait");
                }
            }
            /*  75 */        } catch (Exception e) {
            /*  76 */ e.printStackTrace();
        }
    }

    public void prepareEdit() {
        /*  81 */ this.mode = "Edit";
        /*  82 */ this.showClient = Boolean.valueOf(true);

        /*  84 */ if (this.retrait != null) {
            /*  85 */ this.retrait1 = this.retrait.getMontant();
            /*  86 */ this.commission = this.retrait.getCommission();
            /*  87 */ this.anneeMois = this.retrait.getIdmois();
        }

        try {
            /*  91 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  92 */ if (p != null) {
                /*  93 */ this.showRetraitCreateDialog = Boolean.valueOf(true);
            } else {
                /*  95 */ p = new Privilege();
                /*  96 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 14);
                /*  97 */ if (p != null) {
                    /*  98 */ this.showRetraitCreateDialog = Boolean.valueOf(true);
                } else {
                    /* 100 */ this.showRetraitCreateDialog = Boolean.valueOf(false);
                    /* 101 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de modifier un retrait");
                }
            }
            /* 104 */        } catch (Exception e) {
            /* 105 */ e.printStackTrace();
        }
    }

    public void updateSolde1() {
        /* 112 */ if (this.mode == "Create") {
            /* 113 */ if (this.retrait.getIdclient() != null) {
                /* 114 */ if (this.retrait1 != null) {
                    /* 115 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    /* 116 */ if (this.retrait1.intValue() >= 500) {
                        /* 117 */ if (this.retrait1.intValue() >= 500 && this.retrait1.intValue() <= 20000) {
                            /* 118 */ this.commission = Integer.valueOf(500);
                            /* 119 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 120 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 121 */                        } else if (this.retrait1.intValue() >= 20001 && this.retrait1.intValue() <= 40000) {
                            /* 122 */ this.commission = Integer.valueOf(1000);
                            /* 123 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 124 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 125 */                        } else if (this.retrait1.intValue() >= 40001 && this.retrait1.intValue() <= 60000) {
                            /* 126 */ this.commission = Integer.valueOf(1500);
                            /* 127 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 128 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 129 */                        } else if (this.retrait1.intValue() >= 60001 && this.retrait1.intValue() <= 100000) {
                            /* 130 */ this.commission = Integer.valueOf(2000);
                            /* 131 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 132 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 133 */                        } else if (this.retrait1.intValue() >= 100001 && this.retrait1.intValue() <= 150000) {
                            /* 134 */ this.commission = Integer.valueOf(2500);
                            /* 135 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 136 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 137 */                        } else if (this.retrait1.intValue() >= 150001 && this.retrait1.intValue() <= 200000) {
                            /* 138 */ this.commission = Integer.valueOf(3000);
                            /* 139 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 140 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 141 */                        } else if (this.retrait1.intValue() >= 200001 && this.retrait1.intValue() <= 250000) {
                            /* 142 */ this.commission = Integer.valueOf(3500);
                            /* 143 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 144 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 145 */                        } else if (this.retrait1.intValue() >= 250001 && this.retrait1.intValue() <= 300000) {
                            /* 146 */ this.commission = Integer.valueOf(4000);
                            /* 147 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 148 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 149 */                        } else if (this.retrait1.intValue() >= 300001 && this.retrait1.intValue() <= 350000) {
                            /* 150 */ this.commission = Integer.valueOf(4500);
                            /* 151 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 152 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 153 */                        } else if (this.retrait1.intValue() >= 350001 && this.retrait1.intValue() <= 500000) {
                            /* 154 */ this.commission = Integer.valueOf(5000);
                            /* 155 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 156 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 157 */                        } else if (this.retrait1.intValue() >= 500001 && this.retrait1.intValue() <= 700000) {
                            /* 158 */ this.commission = Integer.valueOf(6000);
                            /* 159 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 160 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 161 */                        } else if (this.retrait1.intValue() >= 700001 && this.retrait1.intValue() <= 800000) {
                            /* 162 */ this.commission = Integer.valueOf(7000);
                            /* 163 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 164 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 165 */                        } else if (this.retrait1.intValue() >= 800001 && this.retrait1.intValue() <= 900000) {
                            /* 166 */ this.commission = Integer.valueOf(8000);
                            /* 167 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 168 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                            /* 169 */                        } else if (this.retrait1.intValue() >= 900001 && this.retrait1.intValue() <= 1000000) {
                            /* 170 */ this.commission = Integer.valueOf(9000);
                            /* 171 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 172 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        } else {
                            /* 174 */ this.commission = Integer.valueOf(this.retrait1.intValue() / 100);
                            /* 175 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                            /* 176 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        }
                    } else {
                        /* 179 */ Client c1 = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                        /* 180 */ this.retrait.getIdclient().setSolde(c1.getSolde());
                        /* 181 */ this.commission = Integer.valueOf(0);
                    }
                } else {
                    /* 184 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    /* 185 */ this.retrait.getIdclient().setSolde(c.getSolde());
                }

            }
            /* 189 */        } else if (this.retrait.getIdclient() != null) {
            /* 190 */ if (this.retrait1 != null) {

                /* 192 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                /* 193 */ if (this.retrait1.intValue() >= 500) {
                    /* 194 */ if (this.retrait1.intValue() >= 500 && this.retrait1.intValue() <= 20000) {
                        /* 195 */ this.commission = Integer.valueOf(500);
                        /* 196 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 197 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 198 */                    } else if (this.retrait1.intValue() >= 20001 && this.retrait1.intValue() <= 40000) {
                        /* 199 */ this.commission = Integer.valueOf(1000);
                        /* 200 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 201 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 202 */                    } else if (this.retrait1.intValue() >= 40001 && this.retrait1.intValue() <= 60000) {
                        /* 203 */ this.commission = Integer.valueOf(1500);
                        /* 204 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 205 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 206 */                    } else if (this.retrait1.intValue() >= 60001 && this.retrait1.intValue() <= 100000) {
                        /* 207 */ this.commission = Integer.valueOf(2000);
                        /* 208 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 209 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 210 */                    } else if (this.retrait1.intValue() >= 100001 && this.retrait1.intValue() <= 150000) {
                        /* 211 */ this.commission = Integer.valueOf(2500);
                        /* 212 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 213 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 214 */                    } else if (this.retrait1.intValue() >= 150001 && this.retrait1.intValue() <= 200000) {
                        /* 215 */ this.commission = Integer.valueOf(3000);
                        /* 216 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 217 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 218 */                    } else if (this.retrait1.intValue() >= 200001 && this.retrait1.intValue() <= 250000) {
                        /* 219 */ this.commission = Integer.valueOf(3500);
                        /* 220 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 221 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 222 */                    } else if (this.retrait1.intValue() >= 250001 && this.retrait1.intValue() <= 300000) {
                        /* 223 */ this.commission = Integer.valueOf(4000);
                        /* 224 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 225 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 226 */                    } else if (this.retrait1.intValue() >= 300001 && this.retrait1.intValue() <= 350000) {
                        /* 227 */ this.commission = Integer.valueOf(4500);
                        /* 228 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 229 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 230 */                    } else if (this.retrait1.intValue() >= 350001 && this.retrait1.intValue() <= 500000) {
                        /* 231 */ this.commission = Integer.valueOf(5000);
                        /* 232 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 233 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 234 */                    } else if (this.retrait1.intValue() >= 500001 && this.retrait1.intValue() <= 700000) {
                        /* 235 */ this.commission = Integer.valueOf(6000);
                        /* 236 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 237 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 238 */                    } else if (this.retrait1.intValue() >= 700001 && this.retrait1.intValue() <= 800000) {
                        /* 239 */ this.commission = Integer.valueOf(7000);
                        /* 240 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 241 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 242 */                    } else if (this.retrait1.intValue() >= 800001 && this.retrait1.intValue() <= 900000) {
                        /* 243 */ this.commission = Integer.valueOf(8000);
                        /* 244 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 245 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                        /* 246 */                    } else if (this.retrait1.intValue() >= 900001 && this.retrait1.intValue() <= 1000000) {
                        /* 247 */ this.commission = Integer.valueOf(9000);
                        /* 248 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 249 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                    } else {
                        /* 251 */ this.commission = Integer.valueOf(this.retrait1.intValue() / 100);
                        /* 252 */ int solde = c.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue();
                        /* 253 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                    }
                } else {
                    /* 256 */ Client c1 = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    /* 257 */ this.retrait.getIdclient().setSolde(c1.getSolde());
                    /* 258 */ this.commission = this.retrait.getCommission();
                }
            } else {
                /* 261 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                /* 262 */ this.retrait.getIdclient().setSolde(c.getSolde());
            }
        }
    }

    public void updateSolde() {
        /* 269 */ this.retrait1 = Integer.valueOf(0);
    }

    public void create() {
        try {
            /* 274 */ if (this.mode.equals("Create")) {

                /* 276 */ if (this.retrait1.intValue() >= 500) {

                    /* 278 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    /* 279 */ if (this.retrait1.intValue() >= 500 && this.retrait1.intValue() <= 20000) {
                        /* 280 */ this.commission = Integer.valueOf(500);
                        /* 281 */                    } else if (this.retrait1.intValue() >= 20001 && this.retrait1.intValue() <= 40000) {
                        /* 282 */ this.commission = Integer.valueOf(1000);
                        /* 283 */                    } else if (this.retrait1.intValue() >= 40001 && this.retrait1.intValue() <= 60000) {
                        /* 284 */ this.commission = Integer.valueOf(1500);
                        /* 285 */                    } else if (this.retrait1.intValue() >= 60001 && this.retrait1.intValue() <= 100000) {
                        /* 286 */ this.commission = Integer.valueOf(2000);
                        /* 287 */                    } else if (this.retrait1.intValue() >= 100001 && this.retrait1.intValue() <= 150000) {
                        /* 288 */ this.commission = Integer.valueOf(2500);
                        /* 289 */                    } else if (this.retrait1.intValue() >= 150001 && this.retrait1.intValue() <= 200000) {
                        /* 290 */ this.commission = Integer.valueOf(3000);
                        /* 291 */                    } else if (this.retrait1.intValue() >= 200001 && this.retrait1.intValue() <= 250000) {
                        /* 292 */ this.commission = Integer.valueOf(3500);
                        /* 293 */                    } else if (this.retrait1.intValue() >= 250001 && this.retrait1.intValue() <= 300000) {
                        /* 294 */ this.commission = Integer.valueOf(4000);
                        /* 295 */                    } else if (this.retrait1.intValue() >= 300001 && this.retrait1.intValue() <= 350000) {
                        /* 296 */ this.commission = Integer.valueOf(4500);
                        /* 297 */                    } else if (this.retrait1.intValue() >= 350001 && this.retrait1.intValue() <= 500000) {
                        /* 298 */ this.commission = Integer.valueOf(5000);
                        /* 299 */                    } else if (this.retrait1.intValue() >= 500001 && this.retrait1.intValue() <= 700000) {
                        /* 300 */ this.commission = Integer.valueOf(6000);
                        /* 301 */                    } else if (this.retrait1.intValue() >= 700001 && this.retrait1.intValue() <= 800000) {
                        /* 302 */ this.commission = Integer.valueOf(7000);
                        /* 303 */                    } else if (this.retrait1.intValue() >= 800001 && this.retrait1.intValue() <= 900000) {
                        /* 304 */ this.commission = Integer.valueOf(8000);
                        /* 305 */                    } else if (this.retrait1.intValue() >= 900001 && this.retrait1.intValue() <= 1000000) {
                        /* 306 */ this.commission = Integer.valueOf(9000);
                    } else {
                        /* 308 */ this.commission = Integer.valueOf(this.retrait1.intValue() / 100);
                    }

                    /* 311 */ Client c1 = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    /* 312 */ c1.setSolde(Integer.valueOf(c1.getSolde().intValue() - this.retrait1.intValue() + this.commission.intValue()));

                    /* 314 */ this.retrait.setIdretrait(this.retraitFacadeLocal.nextLongVal());
                    /* 315 */ this.retrait.setMontant(this.retrait1);
                    /* 316 */ this.retrait.setCommission(this.commission);
                    /* 317 */ this.retrait.setHeure(new Date());
                    /* 318 */ this.retrait.setIdmois(this.anneeMois);
                    /* 319 */ this.retrait.setSolde(Double.valueOf(c.getSolde().doubleValue()));
                    /* 320 */ this.retrait.setCommissionAuto(Boolean.valueOf(true));
                    /* 321 */ this.retraitFacadeLocal.create(this.retrait);

                    /* 323 */ this.clientFacadeLocal.edit(c1);

                    /* 325 */ Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                    /* 326 */ caisse.setMontant(Integer.valueOf(caisse.getMontant().intValue() - this.retrait1.intValue()));
                    /* 327 */ this.caisseFacadeLocal.edit(caisse);

                    /* 329 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du retrait -> client : " + this.retrait.getIdclient().getPrenom() + " " + this.retrait.getIdclient().getNom() + " ; Montant : " + this.retrait1, SessionMBean.getUserAccount());

                    /* 331 */ this.retrait = new Retrait();
                    /* 332 */ JsfUtil.addSuccessMessage("Transaction réussie");
                } else {
                    /* 334 */ JsfUtil.addErrorMessage("Impossible de faire un retrait inferieur a 500 f");
                }

            } /* 338 */ else if (this.retrait != null) {

                /* 340 */ if (this.retrait1.intValue() >= 500) {

                    /* 342 */ if (this.retrait1.intValue() >= 500 && this.retrait1.intValue() <= 20000) {
                        /* 343 */ this.commission = Integer.valueOf(500);
                        /* 344 */                    } else if (this.retrait1.intValue() >= 20001 && this.retrait1.intValue() <= 40000) {
                        /* 345 */ this.commission = Integer.valueOf(1000);
                        /* 346 */                    } else if (this.retrait1.intValue() >= 40001 && this.retrait1.intValue() <= 60000) {
                        /* 347 */ this.commission = Integer.valueOf(1500);
                        /* 348 */                    } else if (this.retrait1.intValue() >= 60001 && this.retrait1.intValue() <= 100000) {
                        /* 349 */ this.commission = Integer.valueOf(2000);
                        /* 350 */                    } else if (this.retrait1.intValue() >= 100001 && this.retrait1.intValue() <= 150000) {
                        /* 351 */ this.commission = Integer.valueOf(2500);
                        /* 352 */                    } else if (this.retrait1.intValue() >= 150001 && this.retrait1.intValue() <= 200000) {
                        /* 353 */ this.commission = Integer.valueOf(3000);
                        /* 354 */                    } else if (this.retrait1.intValue() >= 200001 && this.retrait1.intValue() <= 250000) {
                        /* 355 */ this.commission = Integer.valueOf(3500);
                        /* 356 */                    } else if (this.retrait1.intValue() >= 250001 && this.retrait1.intValue() <= 300000) {
                        /* 357 */ this.commission = Integer.valueOf(4000);
                        /* 358 */                    } else if (this.retrait1.intValue() >= 300001 && this.retrait1.intValue() <= 350000) {
                        /* 359 */ this.commission = Integer.valueOf(4500);
                        /* 360 */                    } else if (this.retrait1.intValue() >= 350001 && this.retrait1.intValue() <= 500000) {
                        /* 361 */ this.commission = Integer.valueOf(5000);
                        /* 362 */                    } else if (this.retrait1.intValue() >= 500001 && this.retrait1.intValue() <= 700000) {
                        /* 363 */ this.commission = Integer.valueOf(6000);
                        /* 364 */                    } else if (this.retrait1.intValue() >= 700001 && this.retrait1.intValue() <= 800000) {
                        /* 365 */ this.commission = Integer.valueOf(7000);
                        /* 366 */                    } else if (this.retrait1.intValue() >= 800001 && this.retrait1.intValue() <= 900000) {
                        /* 367 */ this.commission = Integer.valueOf(8000);
                        /* 368 */                    } else if (this.retrait1.intValue() >= 900001 && this.retrait1.intValue() <= 1000000) {
                        /* 369 */ this.commission = Integer.valueOf(9000);
                    } else {
                        /* 371 */ this.commission = Integer.valueOf(this.retrait1.intValue() / 100);
                    }

                    /* 374 */ Retrait r = this.retraitFacadeLocal.find(this.retrait.getIdretrait());

                    /* 376 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    /* 377 */ c.setSolde(Integer.valueOf(c.getSolde().intValue() + r.getMontant().intValue() + r.getCommission().intValue()));
                    /* 378 */ if (c.getSolde().intValue() < 0) {
                        /* 379 */ c.setSolde(Integer.valueOf(0));
                    }
                    /* 381 */ this.clientFacadeLocal.edit(c);

                    /* 383 */ Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                    /* 384 */ caisse.setMontant(Integer.valueOf(caisse.getMontant().intValue() + r.getMontant().intValue()));

                    /* 386 */ if (caisse.getMontant().intValue() < 0) {
                        /* 387 */ caisse.setMontant(Integer.valueOf(0));
                    }
                    /* 389 */ this.caisseFacadeLocal.edit(caisse);

                    /* 391 */ this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());

                    /* 393 */ this.retrait.setMontant(this.retrait1);
                    /* 394 */ this.retrait.setCommission(this.commission);
                    /* 395 */ this.retrait.setIdmois(this.anneeMois);
                    /* 396 */ this.retraitFacadeLocal.edit(this.retrait);

                    /* 398 */ Client c1 = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    /* 399 */ c1.setSolde(Integer.valueOf(c1.getSolde().intValue() - this.retrait1.intValue() - this.commission.intValue()));
                    /* 400 */ this.clientFacadeLocal.edit(c1);

                    /* 402 */ Caisse caisse1 = this.caisseFacadeLocal.findAll().get(0);
                    /* 403 */ caisse1.setMontant(Integer.valueOf(caisse1.getMontant().intValue() - this.retrait1.intValue()));
                    /* 404 */ this.caisseFacadeLocal.edit(caisse1);

                    /* 406 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification du retrait -> client : " + this.retrait.getIdclient().getPrenom() + " " + this.retrait.getIdclient().getNom() + " Ancien montant : " + r.getMontant() + " ; Nouveau Montant : " + this.retrait1, SessionMBean.getUserAccount());

                    /* 408 */ JsfUtil.addSuccessMessage("Opération réussie");
                } else {
                    /* 410 */ JsfUtil.addErrorMessage("Le montant ne peut etre inferieurà 500 f");
                }
            } else {

                /* 414 */ JsfUtil.addErrorMessage("Aucun retrait selectionné");
            }

            /* 417 */        } catch (Exception e) {
            /* 418 */ e.printStackTrace();
        }
    }

    public void edit() {
    }

    public void delete() {
        try {
            /* 428 */ if (this.retrait != null) {

                try {
                    /* 431 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                    /* 432 */ if (p != null) {
                        /* 433 */ this.showRetraitDeleteDialog = Boolean.valueOf(true);
                    } else {
                        /* 435 */ p = new Privilege();
                        /* 436 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 15);
                        /* 437 */ if (p != null) {
                            /* 438 */ this.showRetraitDeleteDialog = Boolean.valueOf(true);
                        } else {
                            /* 440 */ this.showRetraitDeleteDialog = Boolean.valueOf(false);
                            /* 441 */ JsfUtil.addErrorMessage("Vous n'avez pas le privilège de supprimer ce retrait");
                            return;
                        }
                    }
                    /* 445 */                } catch (Exception e) {
                    /* 446 */ e.printStackTrace();
                }

                /* 449 */ this.retraitFacadeLocal.remove(this.retrait);

                /* 451 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                /* 452 */ c.setSolde(Integer.valueOf(c.getSolde().intValue() + this.retrait.getMontant().intValue()));
                /* 453 */ this.clientFacadeLocal.edit(c);

                /* 455 */ Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                /* 456 */ caisse.setMontant(Integer.valueOf(caisse.getMontant().intValue() + this.retrait.getMontant().intValue()));
                /* 457 */ this.caisseFacadeLocal.edit(caisse);

                /* 459 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du retrait -> client : " + this.retrait.getIdclient().getPrenom() + " " + this.retrait.getIdclient().getNom() + " ; Montant : " + this.retrait.getMontant(), SessionMBean.getUserAccount());

                /* 461 */ JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                /* 463 */ JsfUtil.addErrorMessage("Aucun client selectionnée");
            }
            /* 465 */        } catch (Exception e) {
            /* 466 */ e.printStackTrace();
        }
    }

    public void print() {
        /* 471 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
        /* 472 */ if (p != null) {
            /* 473 */ this.showRetraitPrintDialog = Boolean.valueOf(true);
        } else {
            /* 475 */ p = new Privilege();
            /* 476 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 12);
            /* 477 */ if (p != null) {
                /* 478 */ this.showRetraitPrintDialog = Boolean.valueOf(true);
            } else {
                /* 480 */ this.showRetraitPrintDialog = Boolean.valueOf(false);
                /* 481 */ JsfUtil.addErrorMessage("Vous n'avez pas le privilege d'imprimer le rapport des retrait");
                return;
            }
        }
    }

    private void filterDate(Date date) {
        for (AnneeMois a : this.anneeMoises) {
            try {
                if ((a.getDateDebut().equals(date) || a.getDateDebut().before(date)) && (a.getDateFin().equals(date) || a.getDateFin().after(date))) {
                    this.anneeMois = a;
                    System.err.println("retouvé");
                    break;
                }
            } catch (Exception exception) {
            }
        }
    }
}
