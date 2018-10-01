package cl.gmo.pos.venta.web.Integracion.DTO;

import java.util.ArrayList;

/**
 *
 * @author Advise68
 */
public class UsuarioDTO {
    
    private static final long serialVersionUID = 1L;
    private String descNombreUsuario;
    private String nombreUsuario;
    private String password;
    private int rol;
    private ArrayList<SucursalDTO> sucursales;
    private int cantidadSucursales;
    
    
    public String getDescNombreUsuario() {
		return descNombreUsuario;
	}

	public void setDescNombreUsuario(String descNombreUsuario) {
		this.descNombreUsuario = descNombreUsuario;
	}

	public int getCantidadSucursales() {
		return cantidadSucursales;
	}

	public void setCantidadSucursales(int cantidadSucursales) {
		this.cantidadSucursales = cantidadSucursales;
	}

	public UsuarioDTO() {
        super();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
    
    public ArrayList<SucursalDTO> getSucursales() {
        return sucursales;
    }

    public void setSucursales(ArrayList<SucursalDTO> sucursales) {
        this.sucursales = sucursales;
    } 
    
}