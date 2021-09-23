package io.github.NadhifRadityo.Objects.Library.Constants;

import io.github.NadhifRadityo.Objects.Library.Providers.HTMLDirListingProvider;
import io.github.NadhifRadityo.Objects.Library.Providers.MavenProvider;
import io.github.NadhifRadityo.Objects.Library.Providers.SonatypeProvider;
import io.github.NadhifRadityo.Objects.Library.ThrowsReferencedCallback;

public enum Provider {
	MAVEN(1, MavenProvider.PHASE_PRE, MavenProvider.PHASE_POST, MavenProvider.SEARCH, MavenProvider.DOWNLOAD, MavenProvider.DELETE),
	SONATYPE(2, SonatypeProvider.PHASE_PRE, SonatypeProvider.PHASE_POST, SonatypeProvider.SEARCH, SonatypeProvider.DOWNLOAD, SonatypeProvider.DELETE),
	HTML_DIR_LISTING(3, HTMLDirListingProvider.PHASE_PRE, HTMLDirListingProvider.PHASE_POST, HTMLDirListingProvider.SEARCH, HTMLDirListingProvider.DOWNLOAD, HTMLDirListingProvider.DELETE);

	public final short id;
	public final ThrowsReferencedCallback<Void> PHASE_PRE;
	public final ThrowsReferencedCallback<Void> PHASE_POST;
	public final ThrowsReferencedCallback<JSON_moduleRoot.$dependency[]> SEARCH;
	public final ThrowsReferencedCallback<boolean[]> DOWNLOAD;
	public final ThrowsReferencedCallback<boolean[]> DELETE;

	Provider(int id, ThrowsReferencedCallback<Void> PHASE_PRE, ThrowsReferencedCallback<Void> PHASE_POST, ThrowsReferencedCallback<JSON_moduleRoot.$dependency[]> SEARCH, ThrowsReferencedCallback<boolean[]> DOWNLOAD, ThrowsReferencedCallback<boolean[]> DELETE) {
		this.id = (short) id;
		this.PHASE_PRE = PHASE_PRE;
		this.PHASE_POST = PHASE_POST;
		this.SEARCH = SEARCH;
		this.DOWNLOAD = DOWNLOAD;
		this.DELETE = DELETE;
	}

	public static Provider fromId(short id) {
		for(Provider provider : values())
			if(provider.id == id) return provider;
		return null;
	}
}
