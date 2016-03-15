package sdw.com.bundle;

import org.json.JSONObject;

import android.util.Log;

import work.sdw.R.string;

public class StationBundleObject extends BaseBundleObject
{
	public StationBundleObject(JSONObject jsonObject)
	{
		try
		{
			countyName = jsonObject.getString("countyName");
			administratorName = jsonObject.getString("administratorName");
			controlId = jsonObject.getString("controlId");
			shortTitle=jsonObject.getString("shortTitle");
			name = jsonObject.getString("name");
			address=jsonObject.getString("address");
			coordinateX = jsonObject.getString("coordinateX");
			coordinateY = jsonObject.getString("coordinateY");
			equipment1state = jsonObject.getString("equipment1state");
			equipment2state = jsonObject.getString("equipment2state");
			equipment3state = jsonObject.getString("equipment3state");
			equipment4state = jsonObject.getString("equipment4state");
			equipment5state = jsonObject.getString("equipment5state");
			detection1 = jsonObject.getString("detection1");
			detection2 = jsonObject.getString("detection2");
			detection3 = jsonObject.getString("detection3");
			detection4 = jsonObject.getString("detection4");
			detection5 = jsonObject.getString("detection5");
			detection6 = jsonObject.getString("detection6");
			detection1dl = jsonObject.getString("detection1dl");
			detection1ul = jsonObject.getString("detection1ul");
			detection2dl = jsonObject.getString("detection2dl");
			detection2ul = jsonObject.getString("detection2ul");
			detection3dl = jsonObject.getString("detection3dl");
			detection3ul = jsonObject.getString("detection3ul");
			detection4dl = jsonObject.getString("detection4dl");
			detection4ul = jsonObject.getString("detection4ul");
			detection5dl = jsonObject.getString("detection5dl");
			detection5ul = jsonObject.getString("detection5ul");
			detection6dl = jsonObject.getString("detection6dl");
			detection6ul = jsonObject.getString("detection6ul");
			device_alert = jsonObject.getString("device_alert");
			areaId = jsonObject.getString("areaId");
			control_strategy = jsonObject.getString("control_strategy");			
			administratorId = jsonObject.getString("administratorId");			
			confirmGratingTime = jsonObject.getString("confirmGratingTime");
			gratingDays = jsonObject.getString("gratingDays");
			waterflow = jsonObject.getString("waterflow");
			reduceCOD = jsonObject.getString("reduceCOD");
			reduceNH3N = jsonObject.getString("reduceNH3N");
			reduceP = jsonObject.getString("reduceP");
			sewageId = jsonObject.getString("sewageId");
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}



	private String countyName;
	private String administratorName;
	private String controlId;
	private String areaId;
	private String shortTitle;
	private String name;
	private String address;
	private String coordinateX;
	private String coordinateY;
	private String detection6dl;
	private String detection6ul;
	private String detection5dl;
	private String detection5ul;
	private String detection4dl;
	private String detection4ul;
	private String detection3dl;
	private String detection3ul;
	private String detection2dl;
	private String detection2ul;
	private String detection1dl;
	private String detection1ul;
	private String detection6;
	private String detection5;
	private String detection4;
	private String detection3;
	private String detection2;
	private String detection1;
	private String equipment1state;
	private String equipment2state;
	private String equipment3state;
	private String equipment4state;
	private String equipment5state;
	private String confirmGratingTime;
	private String gratingDays;
	private String reduceCOD;
	private String waterflow;
	private String reduceNH3N;
	private String reduceP;
	private String sewageId;
	private String device_alert;
	private String control_strategy;
	private String administratorId;

	public String getReduceP()
	{
		return reduceP;
	}

	public void setReduceP(String reduceP)
	{
		this.reduceP = reduceP;
	}

	/**
	 * @return the coordinateX
	 */
	public String getCoordinateX()
	{
		return coordinateX;
	}

	/**
	 * @param coordinateX
	 *            the coordinateX to set
	 */
	public void setCoordinateX(String coordinateX)
	{
		this.coordinateX = coordinateX;
	}

	public String getCoordinateY()
	{
		return coordinateY;
	}

	public void setCoordinateY(String coordinateY)
	{
		this.coordinateY = coordinateY;
	}

	public String getAdministratorName()
	{
		return administratorName;
	}

	public void setAdministratorName(String administratorName)
	{
		this.administratorName = administratorName;
	}

	public String getDevice_alert()
	{
		return device_alert;
	}

	public void setDevice_alert(String device_alert)
	{
		this.device_alert = device_alert;
	}

	public String getControlId()
	{
		return controlId;
	}

	public void setControlId(String controlId)
	{
		this.controlId = controlId;
	}

	public String getAreaId()
	{
		return areaId;
	}

	public void setAreaId(String areaId)
	{
		this.areaId = areaId;
	}

	public String getCountyName()
	{
		return countyName;
	}

	public void setCountyName(String countyName)
	{
		this.countyName = countyName;
	}

	public String getControl_strategy()
	{
		return control_strategy;
	}

	public void setControl_strategy(String control_strategy)
	{
		this.control_strategy = control_strategy;
	}

	public String getAdministratorId()
	{
		return administratorId;
	}

	public void setAdministratorId(String administratorId)
	{
		this.administratorId = administratorId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getConfirmGratingTime()
	{
		return confirmGratingTime;
	}

	public void setConfirmGratingTime(String confirmGratingTime)
	{
		this.confirmGratingTime = confirmGratingTime;
	}

	public String getDetection6dl()
	{
		return detection6dl;
	}

	public void setDetection6dl(String detection6dl)
	{
		this.detection6dl = detection6dl;
	}

	public String getDetection6ul()
	{
		return detection6ul;
	}

	public void setDetection6ul(String detection6ul)
	{
		this.detection6ul = detection6ul;
	}

	public String getDetection5dl()
	{
		return detection5dl;
	}

	public void setDetection5dl(String detection5dl)
	{
		this.detection5dl = detection5dl;
	}

	public String getDetection5ul()
	{
		return detection5ul;
	}

	public void setDetection5ul(String detection5ul)
	{
		this.detection5ul = detection5ul;
	}

	public String getDetection4dl()
	{
		return detection4dl;
	}

	public void setDetection4dl(String detection4dl)
	{
		this.detection4dl = detection4dl;
	}

	public String getDetection4ul()
	{
		return detection4ul;
	}

	public void setDetection4ul(String detection4ul)
	{
		this.detection4ul = detection4ul;
	}

	public String getDetection3dl()
	{
		return detection3dl;
	}

	public void setDetection3dl(String detection3dl)
	{
		this.detection3dl = detection3dl;
	}

	public String getDetection3ul()
	{
		return detection3ul;
	}

	public void setDetection3ul(String detection3ul)
	{
		this.detection3ul = detection3ul;
	}

	public String getDetection2dl()
	{
		return detection2dl;
	}

	public void setDetection2dl(String detection2dl)
	{
		this.detection2dl = detection2dl;
	}

	public String getDetection2ul()
	{
		return detection2ul;
	}

	public void setDetection2ul(String detection2ul)
	{
		this.detection2ul = detection2ul;
	}

	public String getDetection1dl()
	{
		return detection1dl;
	}

	public void setDetection1dl(String detection1dl)
	{
		this.detection1dl = detection1dl;
	}

	public String getDetection1ul()
	{
		return detection1ul;
	}

	public void setDetection1ul(String detection1ul)
	{
		this.detection1ul = detection1ul;
	}

	public String getDetection6()
	{
		return detection6;
	}

	public void setDetection6(String detection6)
	{
		this.detection6 = detection6;
	}

	public String getDetection5()
	{
		return detection5;
	}

	public void setDetection5(String detection5)
	{
		this.detection5 = detection5;
	}

	public String getDetection3()
	{
		return detection3;
	}

	public void setDetection3(String detection3)
	{
		this.detection3 = detection3;
	}

	public String getDetection4()
	{
		return detection4;
	}

	public void setDetection4(String detection4)
	{
		this.detection4 = detection4;
	}

	public String getDetection2()
	{
		return detection2;
	}

	public void setDetection2(String detection2)
	{
		this.detection2 = detection2;
	}

	public String getDetection1()
	{
		return detection1;
	}

	public void setDetection1(String detection1)
	{
		this.detection1 = detection1;
	}

	public String getEquipment1state()
	{
		return equipment1state;
	}

	public void setEquipment1state(String equipment1state)
	{
		this.equipment1state = equipment1state;
	}

	public String getEquipment2state()
	{
		return equipment2state;
	}

	public void setEquipment2state(String equipment2state)
	{
		this.equipment2state = equipment2state;
	}

	public String getEquipment3state()
	{
		return equipment3state;
	}

	public void setEquipment3state(String equipment3state)
	{
		this.equipment3state = equipment3state;
	}

	public String getEquipment4state()
	{
		return equipment4state;
	}

	public void setEquipment4state(String equipment4state)
	{
		this.equipment4state = equipment4state;
	}

	public String getEquipment5state()
	{
		return equipment5state;
	}

	public void setEquipment5state(String equipment5state)
	{
		this.equipment5state = equipment5state;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getReduceCOD()
	{
		return reduceCOD;
	}

	public void setReduceCOD(String reduceCOD)
	{
		this.reduceCOD = reduceCOD;
	}

	public String getWaterflow()
	{
		return waterflow;
	}

	public void setWaterflow(String waterflow)
	{
		this.waterflow = waterflow;
	}

	public String getReduceNH3N()
	{
		return reduceNH3N;
	}

	public void setReduceNH3N(String reduceNH3N)
	{
		this.reduceNH3N = reduceNH3N;
	}

	public String getGratingDays()
	{
		return gratingDays;
	}

	public void setGratingDays(String gratingDays)
	{
		this.gratingDays = gratingDays;
	}

	public String getSewageId()
	{
		return sewageId;
	}

	public void setSewageId(String sewageId)
	{
		this.sewageId = sewageId;
	}

	public String getShortTitle()
	{
		return shortTitle;
	}

	public void setShortTitle(String shortTitle)
	{
		this.shortTitle = shortTitle;
	}

}
